package com.volkankelleci.petsocialclub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.volkankelleci.petsocialclub.adapter.PrivateChatAdapter
import com.volkankelleci.petsocialclub.databinding.FragmentPrivateChatBinding
import com.volkankelleci.petsocialclub.util.UserInfo
import com.volkankelleci.petsocialclub.util.Util.database
import kotlinx.android.synthetic.main.fragment_private_chat.*

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
                                val downloadInfos= UserInfo(userUUID,userEmail,userName,userPetName)
                                userInfo.add(downloadInfos)
                            }
                            adapter.notifyDataSetChanged()
                        }
                    }
            }
    }

}