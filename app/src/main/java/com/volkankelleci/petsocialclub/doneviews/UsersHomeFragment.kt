package com.volkankelleci.petsocialclub.doneviews

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.adapter.UserPostAdapter
import com.volkankelleci.petsocialclub.databinding.FragmentUsersHomeBinding
import com.volkankelleci.petsocialclub.util.Post
import com.volkankelleci.petsocialclub.util.Util
import com.volkankelleci.petsocialclub.util.Util.auth
import kotlinx.android.synthetic.main.fragment_users_home.*

class UsersHomeFragment : Fragment() {
    private var _binding: FragmentUsersHomeBinding? = null
    private val binding get() = _binding!!

    private var database: FirebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var recyclerViewAdapter: UserPostAdapter

    var postList = ArrayList<Post>()

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
        getActivity()?.setTitle("PetSocialClub");
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fab.setOnClickListener {
            val action = UsersHomeFragmentDirections.actionUsersHomeFragmentToMessageFragment()
            findNavController().navigate(action)

        }
        //User Name Save
        takesData()
        val layoutManager = LinearLayoutManager(activity)
        usersHomeFragmentRecycler.layoutManager = layoutManager
        recyclerViewAdapter = UserPostAdapter(postList)
        usersHomeFragmentRecycler.adapter = recyclerViewAdapter


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
            val action3 = UsersHomeFragmentDirections.actionUsersHomeFragmentToUserChatFragment()
            findNavController().navigate(action3)

        }
        if (item.itemId == R.id.privateMessageButton) {
            val action4 = UsersHomeFragmentDirections.actionUsersHomeFragmentToPrivateMessageListFragment()
            findNavController().navigate(action4)
        }
        if (item.itemId==R.id.profileButton){
            val action5=UsersHomeFragmentDirections.actionUsersHomeFragmentToUserProfileMenuFragment()
            findNavController().navigate(action5)
        }
        return super.onOptionsItemSelected(item)
    }

    fun takesData() {
        database.collection("Post").orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                } else
                    if (value != null) {
                        if (value.isEmpty == false) {
                            val documents = value.documents
                            postList.clear()
                            for (document in documents) {
                                document.get("Post")
                                val userTitle = document.get("usertitle").toString()
                                val userComment = document.get("usercomment").toString()
                                val userImage = document.get("imageurl").toString()
                                val userEmail = document.get("useremail").toString()

                                val downloadInfos =
                                    Post(userTitle, userComment, userImage, userEmail)
                                postList.add(downloadInfos)

                            }

                        }
                        recyclerViewAdapter.notifyDataSetChanged()
                    }
            }
    }


}