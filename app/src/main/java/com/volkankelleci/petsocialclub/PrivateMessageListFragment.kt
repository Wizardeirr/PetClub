package com.volkankelleci.petsocialclub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.volkankelleci.petsocialclub.adapter.PmRoomAdapter
import com.volkankelleci.petsocialclub.adapter.PrivateMessageListAdapter
import com.volkankelleci.petsocialclub.adapter.UserPostAdapter
import com.volkankelleci.petsocialclub.chatpart.PrivateChatFragmentArgs
import com.volkankelleci.petsocialclub.databinding.FragmentPrivateChatRoomBinding
import com.volkankelleci.petsocialclub.databinding.FragmentPrivateMessageListBinding
import com.volkankelleci.petsocialclub.util.Post
import com.volkankelleci.petsocialclub.util.PrivateMessage
import com.volkankelleci.petsocialclub.util.UserInfo
import com.volkankelleci.petsocialclub.util.Util
import com.volkankelleci.petsocialclub.util.Util.database
import kotlinx.android.synthetic.main.fragment_private_chat_room.*
import kotlinx.android.synthetic.main.fragment_private_message_list.*

class PrivateMessageListFragment: Fragment(R.layout.fragment_private_message_list) {
    private  var _binding:FragmentPrivateMessageListBinding?=null
    private val binding get() =_binding!!
    var userMessage=ArrayList<UserInfo>()
    private lateinit var adapter: PrivateMessageListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentPrivateMessageListBinding.inflate(inflater,container,false)
        val view=binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager=LinearLayoutManager(activity)
        userChatPartRV.layoutManager=layoutManager
        adapter=PrivateMessageListAdapter(userMessage)
        userChatPartRV.adapter=adapter



        takesData()

        fabForPM.setOnClickListener{
            val action=PrivateMessageListFragmentDirections.actionPrivateMessageListFragmentToPrivateChatFragment()
            findNavController().navigate(action)
        }

    }
    private fun takesData() {
        database.collection("userProfileInfo")
            .addSnapshotListener { value, error ->
                if (error != null) {
                } else
                    if (value != null) {
                        if (value.isEmpty == false) {
                            val documents = value.documents
                            userMessage.clear()
                            for (document in documents) {
                                document.get("privateChatInfo")
                                val message = document.get("userName").toString()
                                val userDate = document.get("petName").toString()
                                val toUUID = document.get("userEmail").toString()
                                val userUUID = document.get("PrivateChatUserUUID").toString()
                                val userMail = document.get("userUUID").toString()
                                val userPW=document.get("password").toString()

                                val downloadInfos =UserInfo(message,userUUID,toUUID,userDate,userMail,userPW)
                                userMessage.add(downloadInfos)

                            }

                        }
                        adapter.notifyDataSetChanged()
                    }
            }
    }

}