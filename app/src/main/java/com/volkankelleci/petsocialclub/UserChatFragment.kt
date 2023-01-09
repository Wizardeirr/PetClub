package com.volkankelleci.petsocialclub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FieldValue
import com.google.firebase.ktx.Firebase
import com.volkankelleci.petsocialclub.databinding.FragmentUserChatBinding
import com.volkankelleci.petsocialclub.model.FriendlyMessage
import com.volkankelleci.petsocialclub.util.Util.auth

class UserChatFragment : Fragment() {
    private var _binding: FragmentUserChatBinding? = null
    private val binding get() = _binding!!
    private var db= Firebase.database

    private lateinit var adapter: FriendlyMessageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding=FragmentUserChatBinding.inflate(inflater,container,false)
        val view=binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sendButton.setOnClickListener {
            val gUserChatText=binding.userChatText.text.toString()
            val gUser=auth.currentUser?.email.toString()
            val gdate=FieldValue.serverTimestamp()

            val chatDataMap=HashMap<String,Any>()
            chatDataMap.put("chatGText",gUserChatText)
            chatDataMap.put("chatGUser",gUser)
            chatDataMap.put("chatGDate",gdate)


            val messagesRef = db.reference.child("CHAT")

            val options = FirebaseRecyclerOptions.Builder<FriendlyMessage>()
                .setQuery(messagesRef, FriendlyMessage::class.java)
                .build()
            adapter = ChatRecyclerAdapter(options,)
            manager = LinearLayoutManager(this)
            manager.stackFromEnd = true
            binding.messageRecyclerView.layoutManager = manager
            binding.messageRecyclerView.adapter = adapter

// Scroll down when a new message arrives
// See MyScrollToBottomObserver for details
            adapter.registerAdapterDataObserver(
                MyScrollToBottomObserver(binding.messageRecyclerView, adapter, manager)
            )

    }

}
}
/*  database.collection("UserChatCol").add(chatDataMap).addOnSuccessListener {

                binding.userChatText.setText("")
            }.addOnFailureListener {
                Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
            }
        }*/