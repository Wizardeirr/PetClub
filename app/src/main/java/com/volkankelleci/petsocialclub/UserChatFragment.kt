package com.volkankelleci.petsocialclub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.volkankelleci.petsocialclub.adapter.ChatRecyclerAdapter
import com.volkankelleci.petsocialclub.databinding.FragmentUserChatBinding
import com.volkankelleci.petsocialclub.util.ChatData
import com.volkankelleci.petsocialclub.util.Util.auth
import kotlinx.android.synthetic.main.chat_recycler_raw.*
import kotlinx.android.synthetic.main.fragment_user_chat.*
import java.text.SimpleDateFormat


class UserChatFragment : Fragment() {
    private var _binding: FragmentUserChatBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter:ChatRecyclerAdapter
    private lateinit var firestore:FirebaseFirestore
    var chats=ArrayList<ChatData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore=Firebase.firestore

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



        val layoutManager=LinearLayoutManager(activity)
        userChatRV.layoutManager=layoutManager
        adapter= ChatRecyclerAdapter(chats)
        userChatRV.adapter=adapter


        binding.sendButton.setOnClickListener {
            val gUserChatText=userChatText.text.toString()
            val gUser=auth.currentUser?.email.toString()
            val gdate=FieldValue.serverTimestamp()



            val chatDataMap=HashMap<String,Any>()
            chatDataMap.put("chatGText",gUserChatText)
            chatDataMap.put("chatGUser",gUser)
            chatDataMap.put("chatGDate",gdate)

            firestore.collection("Chats").add(chatDataMap).addOnSuccessListener {

                binding.userChatText.setText("")
            }.addOnFailureListener {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                binding.userChatText.setText("")
            }


    }
        firestore.collection("Chats").orderBy("chatGDate",Query.Direction.ASCENDING).addSnapshotListener { value, error ->
            if (error!=null){
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
            else{
                if (value!=null){
                    if (value.isEmpty){
                        Toast.makeText(requireContext(), "Please Try Again", Toast.LENGTH_SHORT).show()
                    }else{
                        val documents=value.documents
                        chats.clear()


                        for (document in documents){
                            document.get("Chats")
                            val text=document.get("chatGText").toString()
                            val date=document.get("chatGDate").toString()
                            val user=document.get("chatGUser").toString()
                            println(date)

                            val chat=ChatData(text,user,date)
                            chats.add(chat)

                        }

                    }
                    adapter.notifyDataSetChanged()
                }


            }
        }

}

    fun getTimeStamp(seconds :number,anoseconds:number) : Timestamp{

    }


}
