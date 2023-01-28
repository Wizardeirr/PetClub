package com.volkankelleci.petsocialclub.chatpart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.volkankelleci.petsocialclub.adapter.PmRoomAdapter
import com.volkankelleci.petsocialclub.databinding.FragmentPrivateChatRoomBinding
import com.volkankelleci.petsocialclub.util.PrivateMessage
import com.volkankelleci.petsocialclub.util.UserInfo
import com.volkankelleci.petsocialclub.util.Util
import com.volkankelleci.petsocialclub.util.Util.database
import kotlinx.android.synthetic.main.fragment_private_chat_room.*
import kotlinx.android.synthetic.main.pm_raw.*
import kotlinx.android.synthetic.main.private_chat_raw.*
import kotlinx.android.synthetic.main.private_chat_raw.view.*


class PrivateChatFragment : Fragment() {

    private var _binding:FragmentPrivateChatRoomBinding?=null
    private val binding get()=_binding!!
    private lateinit var adapter:PmRoomAdapter
    var user=ArrayList<PrivateMessage>()
    val userPP=ArrayList<UserInfo>()


    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivity()?.setTitle("Private Chat Room")
        firestore = Firebase.firestore
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding=FragmentPrivateChatRoomBinding.inflate(inflater,container,false)
        val view=binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        privateMessageRV.postDelayed({
            privateMessageRV.scrollToPosition(privateMessageRV.adapter!!.itemCount - 1)
        }, 100)
        privateMessageET.setOnClickListener {
            privateMessageRV.postDelayed({
                privateMessageRV.scrollToPosition(privateMessageRV.adapter!!.itemCount - 1)
            }, 100)
        }
        takesUserInfo()

        val layoutManager = LinearLayoutManager(activity)
        privateMessageRV.layoutManager = layoutManager
        adapter = PmRoomAdapter(userPP)
        privateMessageRV.adapter = adapter

        arguments?.let {

            val args=PrivateChatFragmentArgs.fromBundle(it).username
            getActivity()?.setTitle(args)
        }


        binding.privateMessageSendButton.setOnClickListener {
            val userUUID = Util.auth.currentUser!!.uid
            val userEmail = Util.auth.currentUser!!.email.toString()
            val userText =binding.privateMessageET.text.toString()
            val userDate = FieldValue.serverTimestamp()
            val toUUID= arguments?.let {
                PrivateChatFragmentArgs.fromBundle(it).pp
            }

            val userInfoMap = HashMap<String, Any>()
            userInfoMap.put("PrivateChatUserUUID", userUUID)
            userInfoMap.put("PrivateChatUserEmail", userEmail)
            userInfoMap.put("userText",userText)
            userInfoMap.put("userDate",userDate)
            userInfoMap.put("toUUID",toUUID.toString())
            firestore.collection("privateChatInfo/$userUUID/$toUUID").add(userInfoMap).addOnSuccessListener {
                scrollToBottom(privateMessageRV)
                binding.privateMessageET.setText("")
            }
                .addOnFailureListener {
                }
        }
        database.collection("privateChat").orderBy("userDate", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                } else
                    if (value != null) {
                        if (value.isEmpty == false) {
                            val documents = value.documents
                            user.clear()
                            for (document in documents) {
                                document.get("privateChat")
                                val privateMessageUserText = document.get("userText").toString()
                                val privateChatUserUUID = document.get("PrivateChatUserUUID").toString()
                                val privateChatUserEmail = document.get("PrivateChatUserEmail").toString()
                                val privateChatUserDate = document.get("userDate").toString()
                                val privateChatToUUID = document.get("toUUID").toString()
                                val downloadInfos =
                                    PrivateMessage(privateMessageUserText,privateChatUserUUID,privateChatUserDate,privateChatUserEmail,privateChatToUUID)

                                user.add(downloadInfos)
                                adapter.privateChats=user
                            }
                            adapter.notifyDataSetChanged()

                        }

                    }
            }

    }

    private fun scrollToBottom(recyclerView: RecyclerView) {
        // scroll to last item to get the view of last item
        val layoutManager = privateMessageRV.layoutManager as LinearLayoutManager?
        val adapter = privateMessageRV.adapter
        val lastItemPosition = adapter!!.itemCount - 1
        layoutManager!!.scrollToPositionWithOffset(lastItemPosition, 0)
        privateMessageRV.post { // then scroll to specific offset
            val target = layoutManager.findViewByPosition(lastItemPosition)
            if (target != null) {
                val offset = privateMessageRV.measuredHeight - target.measuredHeight
                layoutManager.scrollToPositionWithOffset(lastItemPosition, offset)
            }
        }
    }
    fun takesUserInfo(){
        database.collection("userProfileInfo")
            .addSnapshotListener { value, error ->
                if(error!=null){
                }else
                    if (value!=null){
                        if (value.isEmpty==false){
                            val documents=value.documents
                            userPP
                                .clear()
                            for (document in documents){
                                document.get("userProfileInfo")
                                val userEmail=document.get("userEmail").toString()
                                val userUUID=document.get("userUUID").toString()
                                val userName=document.get("userName").toString()
                                val userPetName=document.get("petName").toString()
                                val userImage=document.get("userImage").toString()
                                val userPassword=document.get("password").toString()
                                val downloadInfos= UserInfo(userUUID,userEmail,userName,userPetName,userImage,userPassword)
                                userPP.add(downloadInfos)

                            }
                            adapter.notifyDataSetChanged()
                        }
                    }
            }
    }
}