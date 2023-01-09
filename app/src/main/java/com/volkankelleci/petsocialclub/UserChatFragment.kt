package com.volkankelleci.petsocialclub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.volkankelleci.petsocialclub.databinding.FragmentUserChatBinding
import com.volkankelleci.petsocialclub.util.ChatData
import com.volkankelleci.petsocialclub.util.Post
import com.volkankelleci.petsocialclub.util.Util.auth
import com.volkankelleci.petsocialclub.util.Util.database
import java.util.UUID

class UserChatFragment : Fragment() {
    private var _binding: FragmentUserChatBinding? = null
    private val binding get() = _binding!!
    private var db= Firebase.database
    var chatList=ArrayList<ChatData>()
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

            database.collection("Chats").add(chatDataMap).addOnSuccessListener {

                binding.userChatText.setText("")
            }.addOnFailureListener {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }

            database.collection("Chats").orderBy("chatGDate",Query.Direction.ASCENDING).addSnapshotListener { value, error ->
                if (error!=null){
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                }
                else{
                    if (value!=null){
                        if (value.isEmpty){
                            Toast.makeText(requireContext(), "Please Try Again", Toast.LENGTH_SHORT).show()

                        }else{
                            val documents=value.documents
                            for (document in documents){
                                val text=document.get("chatGText").toString()
                                val user=document.get("chatGUser").toString()
                                println(text)
                            }
                        }
                    }


                }
            }
    }


}


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}
