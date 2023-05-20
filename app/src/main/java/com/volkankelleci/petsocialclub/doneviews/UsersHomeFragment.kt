package com.volkankelleci.petsocialclub.doneviews

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.post.UserPostAdapter
import com.volkankelleci.petsocialclub.databinding.FragmentUsersHomeBinding
import com.volkankelleci.petsocialclub.data.Post
import com.volkankelleci.petsocialclub.data.UserInfo
import com.volkankelleci.petsocialclub.privatemessagelist.PrivateMessageListFragmentArgs
import com.volkankelleci.petsocialclub.util.Util
import com.volkankelleci.petsocialclub.util.Util.auth
import kotlinx.android.synthetic.main.fragment_users_home.*

class UsersHomeFragment : Fragment() {
    private var _binding: FragmentUsersHomeBinding? = null
    private val binding get() = _binding!!
    private var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var recyclerViewAdapter: UserPostAdapter
    var postList = ArrayList<Post>()
    var pp=ArrayList<UserInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUsersHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        getActivity()?.setTitle("PetSocialClub");
        setHasOptionsMenu(true)
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
        takesPP()
        val layoutManager = LinearLayoutManager(activity)
        usersHomeFragmentRecycler.layoutManager = layoutManager
        recyclerViewAdapter = UserPostAdapter(postList,pp)
        usersHomeFragmentRecycler.adapter = recyclerViewAdapter


    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    private fun takesData() {
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
    fun takesPP() {
        database.collection("userProfileInfo")
            .addSnapshotListener { value, error ->
                if (error != null) {
                } else
                    if (value != null) {
                        if (value.isEmpty == false) {
                            val documents = value.documents
                            pp.clear()
                            for (document in documents) {
                                document.get("userProfileInfo")
                                val usermail = document.get("userEMail").toString()
                                val uuid=document.get("userUUID").toString()
                                val userName = document.get("userName").toString()
                                val userPP = document.get("userImage").toString()
                                val petName = document.get("petName").toString()
                                val pw = document.get("password").toString()

                                val userInfo = UserInfo(uuid,usermail,userName,petName,userPP,pw)
                                pp.add(userInfo)

                            }

                        }
                        recyclerViewAdapter.notifyDataSetChanged()
                    }
            }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.homeButton-> {
                // Ayarlar seçeneği seçildiğinde yapılacak işlemler
                val action=UsersHomeFragmentDirections.actionUsersHomeFragmentSelf()
                findNavController().navigate(action)
                return true
            }
            R.id.messageButton-> {
                val action2=UsersHomeFragmentDirections.actionUsersHomeFragmentToUserChatFragment()
                findNavController().navigate(action2)

                return true

            }
            R.id.privateMessageButton-> {
                val action3=UsersHomeFragmentDirections.actionUsersHomeFragmentToPrivateMessageListFragment(null,null)
                findNavController().navigate(action3)

                return true

            }
            R.id.profileButton-> {
                val action4=UsersHomeFragmentDirections.actionUsersHomeFragmentToUserProfileMenuFragment()
                findNavController().navigate(action4)

                return true

            }
            R.id.logOutButton-> {
                val action5=UsersHomeFragmentDirections.actionUsersHomeFragmentToMainFragment()
                findNavController().navigate(action5)

                return true

            }
            // Diğer menü seçeneklerine göre işlemler burada belirtilebilir
            else -> return super.onOptionsItemSelected(item)
        }
    }


}