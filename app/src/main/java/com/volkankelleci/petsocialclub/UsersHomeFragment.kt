package com.volkankelleci.petsocialclub

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.volkankelleci.petsocialclub.databinding.FragmentUsersHomeBinding
import com.volkankelleci.petsocialclub.util.Post
import com.volkankelleci.petsocialclub.util.Util.auth
import com.volkankelleci.petsocialclub.util.Util.database
import com.volkankelleci.petsocialclub.viewmodel.ProfileFillFragmentViewModel
import kotlinx.android.synthetic.main.fragment_users_home.*

class UsersHomeFragment : Fragment() {
    private var _binding: FragmentUsersHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ProfileFillFragmentViewModel

    private var database: FirebaseFirestore = FirebaseFirestore.getInstance()

    var postList=ArrayList<Post>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUsersHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileFillFragmentViewModel::class.java)
        fab.setOnClickListener {
            val action = UsersHomeFragmentDirections.actionUsersHomeFragmentToMessageFragment()
            findNavController().navigate(action)
            takesData()
        }
        //User Name Save
        binding.userNameText.text= auth.currentUser!!.email
        takesData()

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        requireActivity().menuInflater.inflate(R.menu.user_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logOutButton) {
            try {
                auth.signOut()
                Toast.makeText(activity, "Sign Out Successfully", Toast.LENGTH_SHORT).show()
                val action = UsersHomeFragmentDirections.actionUsersHomeFragmentToMainFragment()
                findNavController().navigate(action)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show()
            }
        }
        if (item.itemId == R.id.homeButton) {
            val action2 = UsersHomeFragmentDirections.actionUsersHomeFragmentSelf()
            findNavController().navigate(action2)
        }
        if (item.itemId == R.id.messageButton) {
            val action3 = UsersHomeFragmentDirections.actionUsersHomeFragmentToMessageFragment()
            findNavController().navigate(action3)
        }
        if (item.itemId == R.id.createButton) {
            val action4 = UsersHomeFragmentDirections.actionUsersHomeFragmentToProfileFillFragment()
            findNavController().navigate(action4)
        }
        if (item.itemId == R.id.profileButton) {
            val action5 = UsersHomeFragmentDirections.actionUsersHomeFragmentToProfileFragment()
            findNavController().navigate(action5)
        }
        return super.onOptionsItemSelected(item)
    }
     fun takesData(){
        var database:FirebaseFirestore=FirebaseFirestore.getInstance()
        database.collection("Post").orderBy("date",Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
            if(error!=null){
            }else
                if (value!=null){
                    if (value.isEmpty==false){
                        val documents=value.documents
                        postList.clear()
                        for (document in documents){
                            document.get("Post")
                            val userTitle=document.get("usertitle").toString()
                            val userComment=document.get("usercomment").toString()
                            val userImage=document.get("imageurl").toString()

                            val downloadInfos=Post(userTitle,userComment,userImage)
                            postList.add(downloadInfos)



                        }
                    }
                }
        }
    }

}