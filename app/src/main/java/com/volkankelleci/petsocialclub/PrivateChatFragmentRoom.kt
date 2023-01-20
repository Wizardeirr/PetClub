package com.volkankelleci.petsocialclub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.volkankelleci.petsocialclub.databinding.FragmentPrivateChatRoomBinding
import com.volkankelleci.petsocialclub.util.UserInfo
import com.volkankelleci.petsocialclub.util.Util
import com.volkankelleci.petsocialclub.util.Util.database
import com.volkankelleci.petsocialclub.util.Util.storage


class PrivateChatFragmentRoom : Fragment() {

    private var _binding:FragmentPrivateChatRoomBinding?=null
    private val binding get()=_binding!!
    private val user=UserInfo("","","","","")

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
            privateChatInfoTake()
        }


    }
    private fun privateChatInfoTake(){
        val userUUID = Util.auth.currentUser!!.uid
        val userEmail = Util.auth.currentUser!!.email.toString()
        val userID= user.uuid



        val userInfoMap = java.util.HashMap<String, Any>()
        userInfoMap.put("PrivateChatUserUUID", userUUID)
        userInfoMap.put("PrivateChatUserEmail", userEmail)
        userInfoMap.put("PrivateChatUserID", userID!!)
        Util.database.collection("privateChat").add(userInfoMap).addOnSuccessListener {
            Toast.makeText(requireContext(), "DONE", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener {
            }
    }
}