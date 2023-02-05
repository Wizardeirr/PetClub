package com.volkankelleci.petsocialclub.chatpart

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.Date
import com.google.type.DateTime
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.adapter.PmRoomAdapter
import com.volkankelleci.petsocialclub.databinding.FragmentPrivateChatRoomBinding
import com.volkankelleci.petsocialclub.util.CounterNot
import com.volkankelleci.petsocialclub.util.PrivateMessage
import com.volkankelleci.petsocialclub.util.Util.auth
import com.volkankelleci.petsocialclub.util.Util.database
import kotlinx.android.synthetic.main.fragment_private_chat_room.*
import kotlinx.android.synthetic.main.fragment_user_chat.*
import java.text.SimpleDateFormat


class PrivateChatFragment : Fragment() {
    private var _binding:FragmentPrivateChatRoomBinding?=null
    private val binding get()=_binding!!
    private lateinit var adapter:PmRoomAdapter
    var user=ArrayList<PrivateMessage>()
    val layoutManager = LinearLayoutManager(activity)
    private lateinit var firestore: FirebaseFirestore

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
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Scroll item 2 to 20 pixels from the top

        //Notification
        showNotification()

        privateMessageRV.postDelayed({
            privateMessageRV.scrollToPosition(privateMessageRV.adapter!!.itemCount - 1)
        }, 100)
        privateMessageET.setOnClickListener {
            privateMessageRV.postDelayed({
                privateMessageRV.scrollToPosition(privateMessageRV.adapter!!.itemCount - 1)
            }, 100)

        }

        privateMessageRV.layoutManager = layoutManager
        adapter = PmRoomAdapter()
        privateMessageRV.adapter = adapter

        val new=arguments?.let {
            val args=PrivateChatFragmentArgs.fromBundle(it).username
            getActivity()?.setTitle("${args}")
        }
        binding.privateMessageSendButton.setOnClickListener {
            val userEmail = auth.currentUser!!.email.toString()
            val userText =binding.privateMessageET.text.toString()
            val userDate = FieldValue.serverTimestamp()
            val userUUID = auth.currentUser!!.uid
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
            firestore.collection("privateChatInfo/$toUUID/$userUUID").add(userInfoMap).addOnSuccessListener {
                scrollToBottom(privateMessageRV)
                binding.privateMessageET.setText("")
            }
        }
        val toUUID= arguments?.let {
            PrivateChatFragmentArgs.fromBundle(it).pp
        }
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
                                val privateChatToUUID = document.get("toUUID").toString()
                                val downloadInfos =PrivateMessage(privateMessageUserText,privateChatUserUUID,privateChatToUUID,privateChatUserDate,privateChatUserEmail)
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
    private fun scrollToBottom(recyclerView: RecyclerView) {
        // scroll to last item to get the view of last item
        val layoutManager = privateMessageRV.layoutManager as LinearLayoutManager?
        val adapter = privateMessageRV.adapter
        val lastItemPosition = adapter!!.itemCount - 1
        layoutManager!!.scrollToPositionWithOffset(lastItemPosition, 0)
        privateMessageRV.post {
            val target = layoutManager.findViewByPosition(lastItemPosition)
            if (target != null) {
                val offset = privateMessageRV.measuredHeight - target.measuredHeight
                layoutManager.scrollToPositionWithOffset(lastItemPosition, offset)
            }
        }
    }
    private fun recyclerPosition(){
        privateMessageRV.post(Runnable { privateMessageRV.smoothScrollToPosition(adapter.itemCount - 1) })
    }
    fun showNotification(title: String, message: String) {
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CounterNot.COUNTER_CHANNEL_ID,"Counter",NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "New message"
            mNotificationManager.createNotificationChannel(channel)
        }
        val mBuilder = NotificationCompat.Builder(activity, "YOUR_CHANNEL_ID")
            .setSmallIcon(R.mipmap.ic_launcher) // notification icon
            .setContentTitle(title) // title for notification
            .setContentText(message)// message for notification
            .setAutoCancel(true) // clear notification after click
        val intent = Intent(context, ACTIVITY_NAME::class.java)
        val pi = PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        mBuilder.setContentIntent(pi)
        mNotificationManager.notify(0, mBuilder.build())
    }

}