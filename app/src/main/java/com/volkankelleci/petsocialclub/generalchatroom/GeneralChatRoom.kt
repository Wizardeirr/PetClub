package com.volkankelleci.petsocialclub.generalchatroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.volkankelleci.petsocialclub.databinding.FragmentUserChatBinding
import com.volkankelleci.petsocialclub.data.ChatData
import com.volkankelleci.petsocialclub.util.Util.auth
import com.volkankelleci.petsocialclub.util.Util.database
import kotlinx.android.synthetic.main.fragment_user_chat.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class GeneralChatRoom : Fragment() {

    private var _binding: FragmentUserChatBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ChatRecyclerAdapter
    var chats = ArrayList<ChatData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserChatBinding.inflate(inflater, container, false)
        val view = binding.root
        getActivity()?.setTitle("Chat Room")
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        downForNewMessage.visibility=View.INVISIBLE
        aboutVisibilityOfDownButton()

        //Showing message automatically in RecyclerView End
        userChatRV.postDelayed({
            userChatRV.scrollToPosition(userChatRV.adapter!!.itemCount - 1)
        }, 100)

        sendButton.setOnClickListener {
            userChatRV.postDelayed({
                userChatRV.scrollToPosition(userChatRV.adapter!!.itemCount -1)
            }, 100)
        }
        userChatText.setOnClickListener {
            userChatRV.postDelayed({
                userChatRV.scrollToPosition(userChatRV.adapter!!.itemCount -1)
            }, 100)
        }

        // Adapter
        val layoutManager = LinearLayoutManager(activity)
        userChatRV.layoutManager = layoutManager
        adapter = ChatRecyclerAdapter()
        userChatRV.adapter = adapter
        layoutManager.setStackFromEnd(true);

        //adding informations to Firebase what we want
        binding.sendButton.setOnClickListener {
            val gUserChatText = userChatText.text.toString()
            val gUser = auth.currentUser?.email.toString()
            val gdate = FieldValue.serverTimestamp()

            val chatDataMap = HashMap<String, Any>()
            chatDataMap.put("chatGText", gUserChatText)
            chatDataMap.put("chatGUser", gUser)
            chatDataMap.put("chatGDate", gdate)

            database.collection("Chats").add(chatDataMap).addOnSuccessListener {
                scrollToBottom(userChatRV)
                binding.userChatText.setText("")
            }.addOnFailureListener {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                binding.userChatText.setText("")
            }
        }

        //taking from Firestore
        database.collection("Chats").orderBy("chatGDate", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                } else {
                    if (value != null) {
                        if (value.isEmpty) {
                            Toast.makeText(requireContext(), "Please Try Again", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            val documents = value.documents
                            chats.clear()
                            for (document in documents) {
                                document.get("Chats")
                                val text = document.get("chatGText").toString()
                                val date = document.get("chatGDate").toString()
                                val user = document.get("chatGUser").toString()
                                val chat = ChatData(text, user,date)
                                chats.add(chat)
                                adapter.chats = chats
                                userChatRV.scrollToPosition(userChatRV.adapter!!.itemCount -1)
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }
    }

    private fun scrollToBottom(recyclerView: RecyclerView) {
        // scroll to last item to get the view of last item
        val layoutManager = userChatRV.layoutManager as LinearLayoutManager?

        val lastItemPosition = adapter.itemCount - 1
        layoutManager!!.scrollToPositionWithOffset(lastItemPosition, 0)
        userChatRV.post { // then scroll to specific offset
            val target = layoutManager.findViewByPosition(lastItemPosition)
            if (target != null) {
                val offset = userChatRV.measuredHeight - target.measuredHeight
                layoutManager.scrollToPositionWithOffset(lastItemPosition, offset)
            }
        }
    }
    private fun aboutVisibilityOfDownButton(){
        if(userChatText.text==null){
            userChatRV.scrollToPosition(userChatRV.adapter!!.itemCount -1)
        }
    }
}
