package com.volkankelleci.petsocialclub.userslist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.volkankelleci.petsocialclub.data.UserInfo
import com.volkankelleci.petsocialclub.databinding.FragmentUserListBinding
import com.volkankelleci.petsocialclub.util.Util.database
import kotlinx.android.synthetic.main.fragment_user_list.*

class UserListFragment : Fragment(),UserListAdapter.Listener {

    private  var _binding:FragmentUserListBinding?=null
    private val binding get() = _binding!!
    private lateinit var adapter: UserListAdapter
    var userInfo=ArrayList<UserInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        val view = binding.root
        getActivity()?.setTitle("Private Message")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // fun called
        takesUserInfo()

        // adapter created
        val layoutManager= LinearLayoutManager(activity)
        privateChatRV.layoutManager=layoutManager
        adapter= UserListAdapter(userInfo,this@UserListFragment)
        privateChatRV.adapter=adapter

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

    override fun onItemClickListener(userList: UserInfo) {
        println(userList.uuid)
        println(userList.userName)
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("toUUID", userList.uuid)
        editor.apply()
    }

}