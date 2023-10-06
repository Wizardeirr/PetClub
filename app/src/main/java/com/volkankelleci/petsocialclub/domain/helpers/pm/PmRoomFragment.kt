package com.volkankelleci.petsocialclub.domain.helpers.pm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.volkankelleci.petsocialclub.data.PrivateMessage
import com.volkankelleci.petsocialclub.databinding.FragmentPrivateChatRoomBinding
import com.volkankelleci.petsocialclub.domain.helpers.lastprivatemessagelist.LastPrivateMessageListFragment
import com.volkankelleci.petsocialclub.util.Util.auth
import com.volkankelleci.petsocialclub.util.Util.database
import kotlinx.android.synthetic.main.fragment_private_chat_room.privateMessageET
import kotlinx.android.synthetic.main.fragment_private_chat_room.privateMessageRV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject


class PmRoomFragment @Inject constructor(
    private var adapter: PmRoomAdapter

): Fragment() {
    private var _binding:FragmentPrivateChatRoomBinding?=null
    private val binding get()=_binding!!
    var user=ArrayList<PrivateMessage>()
    val layoutManager = LinearLayoutManager(activity)
    private lateinit var firestore: FirebaseFirestore
    private lateinit var toUUID: String
    private val uuidList = mutableSetOf<String>()
    private lateinit var userName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore = Firebase.firestore
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding=FragmentPrivateChatRoomBinding.inflate(inflater,container,false)
        val view=binding.root
        activity?.title = ""

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        privateMessageRV.postDelayed({
            privateMessageRV.scrollToPosition(privateMessageRV.adapter!!.itemCount - 1)
        }, 500)
        privateMessageET.setOnClickListener {
            privateMessageRV.postDelayed({
                privateMessageRV.scrollToPosition(privateMessageRV.adapter!!.itemCount - 1)
            }, 500)

        }
        //determined to RV
        privateMessageRV.layoutManager = layoutManager
        adapter = PmRoomAdapter()
        privateMessageRV.adapter = adapter

        // toUUID taking
        toUUID= arguments?.let{
            PmRoomFragmentArgs.fromBundle(it).toUUID
        }?:""


        //userName Taking
        userName=arguments?.let {
            PmRoomFragmentArgs.fromBundle(it).username
        }?:""
        activity?.title = userName
        //When Send button click what we do
        binding.privateMessageSendButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val userEmail = auth.currentUser!!.email.toString()
                val userText =binding.privateMessageET.text.toString()
                val timeStamp= Instant.now()
                //timeFormatter
                val localDateTime = LocalDateTime.ofInstant(timeStamp, ZoneId.systemDefault())
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val userDate = localDateTime.format(formatter)

                //timeFormatter Done
                val userUUID = auth.currentUser!!.uid
                val userInfoMap = HashMap<String, Any>()
                userInfoMap.put("PrivateChatUserUUID", userUUID)
                userInfoMap.put("PrivateChatUserEmail", userEmail)
                userInfoMap.put("userText",userText)
                userInfoMap.put("userDate",userDate)
                userInfoMap.put("toUUID",toUUID)
                firestore.collection("privateChatInfo/$userUUID/$toUUID").add(userInfoMap).addOnSuccessListener {
                    scrollToBottom(privateMessageRV)
                    binding.privateMessageET.setText("")
                }
                    .addOnFailureListener {
                    }
                firestore.collection("privateChatInfo/$toUUID/$userUUID").add(userInfoMap).addOnSuccessListener {
                    scrollToBottom(privateMessageRV)
                    binding.privateMessageET.setText("")
                }
            }

        }




        //taking user texts to collection and saving to list of adapter. For show on Adapter

        database.collection("privateChatInfo/$toUUID/${auth.currentUser!!.uid}").orderBy("userDate",Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                } else
                    if (value != null) {
                        if (value.isEmpty == false) {
                            val documents = value.documents
                            user.clear()
                            for (document in documents) {
                                document.get("privateChatInfo")
                                val privateMessageUserText = document.get("userText").toString()
                                val privateChatUserUUID = document.get("PrivateChatUserUUID").toString()
                                val privateChatUserEmail = document.get("PrivateChatUserEmail").toString()
                                val privateChatUserDate = document.get("userDate").toString()
                                val downloadInfos = PrivateMessage(privateMessageUserText,privateChatUserUUID,toUUID,privateChatUserDate,privateChatUserEmail)
                                user.add(downloadInfos)
                                adapter.privateChats=user

                                if (adapter.privateChats==user){
                                    privateMessageRV.scrollToPosition(privateMessageRV.adapter!!.itemCount -1)
                                }
                            }
                            adapter.notifyDataSetChanged()
                        }
                    }
            }
    }

    //For When user send message i want to show last message.
    private fun scrollToBottom(recyclerView: RecyclerView) {
        // scroll to last item to get the view of last item

        val layoutManager = privateMessageRV.layoutManager as LinearLayoutManager?
        if (layoutManager!=null){
            val adapter = privateMessageRV.adapter
            if (adapter!=null){
                val lastItemPosition = adapter.itemCount - 1
                layoutManager.scrollToPositionWithOffset(lastItemPosition, 0)
                privateMessageRV.post {
                    val target = layoutManager.findViewByPosition(lastItemPosition)
                    if (target != null) {
                        val offset = privateMessageRV.measuredHeight - target.measuredHeight
                        layoutManager.scrollToPositionWithOffset(lastItemPosition, offset)
                    }
                }
            }

        }

    }
    // When i click to back u could move  trustly without crash  to back
        override fun onResume() {
            super.onResume()
        val userUUID = auth.currentUser!!.uid
        toUUID= arguments?.let{
            PmRoomFragmentArgs.fromBundle(it).toUUID
        }?:""
        val bundle = Bundle()
        bundle.putString("toUUID", toUUID ?: "")

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val action=PmRoomFragmentDirections.actionPmRoomFragmentToLastPrivateMessageListFragment(
                requireArguments().toString(),userUUID)
            Navigation.findNavController(requireView()).navigate(action)
        }

    }


}