package com.volkankelleci.petsocialclub.domain.helpers.userslist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.data.UserInfo
import com.volkankelleci.petsocialclub.databinding.FragmentUserListBinding
import com.volkankelleci.petsocialclub.util.Util.database
import javax.inject.Inject


class UserListFragment @Inject constructor(
    private var adapter: UserListAdapter

): Fragment() {

    private  var _binding:FragmentUserListBinding?=null
    private val binding get() = _binding!!
    var userInfo=ArrayList<UserInfo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        val view = binding.root
        getActivity()?.setTitle("Select Person")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Enable the ActionBar

        // fun called
        takesUserInfo()
        setHasOptionsMenu(true)
        // adapter created

        val layoutManager= LinearLayoutManager(activity)
        binding.privateChatRV.layoutManager=layoutManager
        binding.privateChatRV.adapter=adapter

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.private_message_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun takesUserInfo(){
        database.collection("userProfileInfo")
            .addSnapshotListener { value, error ->
                if(error!=null){
                }else
                    if (value!=null){
                        if (value.isEmpty==false){
                            val documents=value.documents
                            userInfo.clear()
                            for (document in documents){
                                document.get("userProfileInfo")
                                val userEmail=document.get("userEmail").toString()
                                val userUUID=document.get("userUUID").toString()
                                val userName=document.get("userName").toString()
                                val userPetName=document.get("petName").toString()
                                val userImage=document.get("userImage").toString()
                                val userPassword=document.get("password").toString()
                                val downloadInfos= UserInfo(userUUID,userEmail,userName,userPetName,userImage,userPassword)
                                userInfo.add(downloadInfos)
                            }
                            adapter.notifyDataSetChanged()
                        }
                    }
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            activity?.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item)
    }

}