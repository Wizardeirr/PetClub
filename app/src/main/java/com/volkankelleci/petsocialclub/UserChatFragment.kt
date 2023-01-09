package com.volkankelleci.petsocialclub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FieldValue
import com.google.firebase.ktx.Firebase
import com.volkankelleci.petsocialclub.databinding.FragmentUserChatBinding
import com.volkankelleci.petsocialclub.util.ChatData
import com.volkankelleci.petsocialclub.util.Post
import com.volkankelleci.petsocialclub.util.Util.auth
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

            val allChatInfo=ChatData(gUserChatText,gUser,gdate)
            chatList.add(allChatInfo)
            val chatReference=db.getReference("UserChatCol")
            chatReference.setValue(gUserChatText)




    }

}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}
