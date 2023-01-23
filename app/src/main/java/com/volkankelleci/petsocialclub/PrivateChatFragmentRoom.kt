package com.volkankelleci.petsocialclub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.volkankelleci.petsocialclub.adapter.PmRoomAdapter
import com.volkankelleci.petsocialclub.adapter.UserRecyclerAdapter
import com.volkankelleci.petsocialclub.databinding.FragmentPrivateChatRoomBinding
import com.volkankelleci.petsocialclub.doneviews.MessageFragmentDirections
import com.volkankelleci.petsocialclub.util.Post
import com.volkankelleci.petsocialclub.util.PrivateMessage
import com.volkankelleci.petsocialclub.util.UserInfo
import com.volkankelleci.petsocialclub.util.Util
import com.volkankelleci.petsocialclub.util.Util.auth
import com.volkankelleci.petsocialclub.util.Util.database
import com.volkankelleci.petsocialclub.util.Util.storage
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.fragment_private_chat_room.*
import kotlinx.android.synthetic.main.fragment_users_home.*


class PrivateChatFragmentRoom : Fragment() {

    private var _binding:FragmentPrivateChatRoomBinding?=null
    private val binding get()=_binding!!
    private lateinit var adapter:PmRoomAdapter
    private val user=ArrayList<PrivateMessage>()
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivity()?.setTitle("Private Chat Room")
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
        val layoutManager = LinearLayoutManager(activity)
        privateMessageRV.layoutManager = layoutManager
        adapter = PmRoomAdapter()
        privateMessageRV.adapter = adapter


        binding.privateMessageSendButton.setOnClickListener {
            val userUUID = Util.auth.currentUser!!.uid
            val userEmail = Util.auth.currentUser!!.email.toString()
            val userText =binding.privateMessageET.text.toString()
            val userInfoMap = java.util.HashMap<String, Any>()
            userInfoMap.put("PrivateChatUserUUID", userUUID)
            userInfoMap.put("PrivateChatUserEmail", userEmail)
            userInfoMap.put("userText",userText)
            Util.database.collection("privateChat").add(userInfoMap).addOnSuccessListener {
                Toast.makeText(requireContext(), "DONE", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                }
        }

    }
    private fun takesData() {
        database.collection("Post").orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                } else
                    if (value != null) {
                        if (value.isEmpty == false) {
                            val documents = value.documents

                            for (document in documents) {
                                document.get("Post")
                                val userTitle = document.get("usertitle").toString()
                                val userComment = document.get("usercomment").toString()
                                val userImage = document.get("imageurl").toString()
                                val userEmail = document.get("useremail").toString()

                                val downloadInfos =
                                    Post(userTitle, userComment, userImage, userEmail)


                            }

                        }

                    }
            }
    }

}