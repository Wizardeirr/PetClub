package com.volkankelleci.petsocialclub.domain.helpers.postandhome

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.data.Post
import com.volkankelleci.petsocialclub.data.UserInfo
import com.volkankelleci.petsocialclub.databinding.FragmentUsersHomeBinding
import com.volkankelleci.petsocialclub.util.Constants.HOME_FRAGMENT_TITLE
import com.volkankelleci.petsocialclub.viewmodel.UsersHomeFragmentVM
import kotlinx.android.synthetic.main.fragment_users_home.fab
import kotlinx.android.synthetic.main.fragment_users_home.usersHomeFragmentRecycler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class UsersHomeFragment @Inject constructor(
    private var recyclerViewAdapter: UserPostAdapter


): Fragment() {
    private var _binding: FragmentUsersHomeBinding? = null
    private val binding get() = _binding!!
    private var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var pp = ArrayList<UserInfo>()
    private val viewModel: UsersHomeFragmentVM by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUsersHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        activity?.title = HOME_FRAGMENT_TITLE
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
        takesPP()
        //adapter determined
        val layoutManager = LinearLayoutManager(activity)
        usersHomeFragmentRecycler.layoutManager = layoutManager
        usersHomeFragmentRecycler.adapter = recyclerViewAdapter

        //popupMenu


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    private fun observePostList(){
        lifecycleScope.launchWhenStarted {

        }
    }


    private fun takesPP() {
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
                                val uuid = document.get("userUUID").toString()
                                val userName = document.get("userName").toString()
                                val userPP = document.get("userImage").toString()
                                val petName = document.get("petName").toString()
                                val pw = document.get("password").toString()

                                val userInfo =
                                    UserInfo(uuid, usermail, userName, petName, userPP, pw)
                                pp.add(userInfo)

                            }

                        }
                        recyclerViewAdapter.notifyDataSetChanged()
                    }
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeButton -> {
                val action = UsersHomeFragmentDirections.actionUsersHomeFragmentSelf()
                findNavController().navigate(action)
                return true
            }

            R.id.messageButton -> {
                val action2 =
                    UsersHomeFragmentDirections.actionUsersHomeFragmentToUserChatFragment()
                findNavController().navigate(action2)

                return true

            }

            R.id.privateMessageButton -> {

                val action3 =
                    UsersHomeFragmentDirections.actionUsersHomeFragmentToLastPrivateMessageListFragment(
                        requireArguments().toString(), ""
                    )
                findNavController().navigate(action3)

                return true

            }

            R.id.profileButton -> {
                val action4 =
                    UsersHomeFragmentDirections.actionUsersHomeFragmentToUserProfileMenuFragment()
                findNavController().navigate(action4)

                return true

            }

            R.id.logOutButton -> {
                val action5 = UsersHomeFragmentDirections.actionUsersHomeFragmentToMainFragment()
                findNavController().navigate(action5)
                FirebaseAuth.getInstance().signOut()
                return true

            }
            // Diğer menü seçeneklerine göre işlemler burada belirtilebilir
            else -> return super.onOptionsItemSelected(item)
        }
    }
    override fun onResume() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }
        super.onResume()
    }


}