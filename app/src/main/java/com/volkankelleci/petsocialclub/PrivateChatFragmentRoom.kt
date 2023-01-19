package com.volkankelleci.petsocialclub

import android.R
import android.app.ActionBar
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.data.model.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.volkankelleci.petsocialclub.databinding.FragmentPrivateChatRoomBinding
import com.volkankelleci.petsocialclub.util.UserInfo
import com.volkankelleci.petsocialclub.util.Util
import kotlinx.android.synthetic.main.fragment_private_chat_room.*
import kotlinx.android.synthetic.main.fragment_user_chat.*


class PrivateChatFragmentRoom : Fragment() {

    private var _binding:FragmentPrivateChatRoomBinding?=null
    private val binding get()=_binding!!
    private val userInfo:UserInfo?=null
    private lateinit var firestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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


        binding.privateMessageSendButton.setOnClickListener {
            val privateMessage = binding.privateMessageET.text.toString()
            val gUser = Util.auth.currentUser!!.uid
            val gdate = FieldValue.serverTimestamp()
            val toId = userInfo!!.uuid



            val privateChatDataMap = HashMap<String, Any>()
            privateChatDataMap.put("PrivateChatGText", privateMessage)
            privateChatDataMap.put("PrivateChatGUser", gUser)
            privateChatDataMap.put("PrivateChatGDate", gdate)
            privateChatDataMap.put("PrivateToID",toId)

            firestore.collection("PrivateChats").add(privateChatDataMap).addOnSuccessListener {
                binding.privateMessageET.setText("")
            }.addOnFailureListener {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                binding.privateMessageET.setText("")
            }


        }


    }
}