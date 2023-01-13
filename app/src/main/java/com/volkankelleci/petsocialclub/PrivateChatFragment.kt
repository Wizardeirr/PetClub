package com.volkankelleci.petsocialclub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.Query
import com.volkankelleci.petsocialclub.adapter.ChatRecyclerAdapter
import com.volkankelleci.petsocialclub.adapter.PrivateChatAdapter
import com.volkankelleci.petsocialclub.adapter.UserRecyclerAdapter
import com.volkankelleci.petsocialclub.databinding.FragmentPrivateChatBinding
import com.volkankelleci.petsocialclub.util.Post
import com.volkankelleci.petsocialclub.util.UserInfo
import com.volkankelleci.petsocialclub.util.Util
import com.volkankelleci.petsocialclub.util.Util.auth
import com.volkankelleci.petsocialclub.util.Util.database
import kotlinx.android.synthetic.main.fragment_private_chat.*
import kotlinx.android.synthetic.main.fragment_users_home.*

class PrivateChatFragment : Fragment() {

    private  var _binding:FragmentPrivateChatBinding?=null
    private val binding get() = _binding!!
    private lateinit var adapter: PrivateChatAdapter
    var userInfo=ArrayList<UserInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPrivateChatBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        takesUserInfo()
        val layoutManager= LinearLayoutManager(activity)
        privateChatRV.layoutManager=layoutManager
        adapter= PrivateChatAdapter(userInfo)
        privateChatRV.adapter=adapter



    }

    fun takesUserInfo(){
        database.collection("UserInfo")
            .addSnapshotListener { value, error ->
                if(error!=null){
                }else
                    if (value!=null){
                        if (value.isEmpty==false){
                            val documents=value.documents


                            userInfo.clear()
                            for (document in documents){
                                document.get("UserInfo")
                                val userEmail=document.get("userEmail").toString()
                                val userUUID=document.get("userUUID").toString()
                                val downloadInfos= UserInfo(userUUID,userEmail)
                                userInfo.add(downloadInfos)



                            }
                            adapter.notifyDataSetChanged()
                        }
                    }
            }
    }

}