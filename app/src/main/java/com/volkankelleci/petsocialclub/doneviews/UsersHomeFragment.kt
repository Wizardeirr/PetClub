package com.volkankelleci.petsocialclub.doneviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.data.Post
import com.volkankelleci.petsocialclub.data.UserInfo
import com.volkankelleci.petsocialclub.databinding.FragmentUsersHomeBinding
import com.volkankelleci.petsocialclub.post.UserPostAdapter
import com.volkankelleci.petsocialclub.util.BaseViewBindingFragment
import com.volkankelleci.petsocialclub.util.Util.getToUUIDFromSharedPreferences

class UsersHomeFragment : BaseViewBindingFragment<FragmentUsersHomeBinding>(),UserPostAdapter.Listener {
    private var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var recyclerViewAdapter: UserPostAdapter
    var postList = ArrayList<Post>()
    var pp=ArrayList<UserInfo>()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUsersHomeBinding {
        return FragmentUsersHomeBinding.inflate(inflater,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivity()?.setTitle("PetSocialClub");
        setHasOptionsMenu(true)
        binding.fab.setOnClickListener {
            val action = UsersHomeFragmentDirections.actionUsersHomeFragmentToMessageFragment()
            findNavController().navigate(action)
        }

        //User Name Save
        takesData()
        takesPP()
        //adapter determined
        val layoutManager = LinearLayoutManager(activity)
        binding.usersHomeFragmentRecycler.layoutManager = layoutManager
        recyclerViewAdapter = UserPostAdapter(postList,pp,this@UsersHomeFragment)
        binding.usersHomeFragmentRecycler.adapter = recyclerViewAdapter

        //popupMenu

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

                val action3=UsersHomeFragmentDirections.actionUsersHomeFragmentToLastPrivateMessageListFragment(
                    getToUUIDFromSharedPreferences(requireContext()),""
                )
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
                FirebaseAuth.getInstance().signOut()
                return true

            }
            // Diğer menü seçeneklerine göre işlemler burada belirtilebilir
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClickListener(postList: Post) {
        val action=UsersHomeFragmentDirections.actionUsersHomeFragmentToMapsActivity()
        findNavController().navigate(action)
    }


}