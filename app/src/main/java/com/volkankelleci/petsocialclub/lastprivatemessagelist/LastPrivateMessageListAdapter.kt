package com.volkankelleci.petsocialclub.lastprivatemessagelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.PetSocialClubApplication
import com.volkankelleci.petsocialclub.data.PrivateMessage
import com.volkankelleci.petsocialclub.databinding.ChatListRawBinding // chat_list_raw.xml için ViewBinding
import com.volkankelleci.petsocialclub.util.Util.createPlaceHolder
import com.volkankelleci.petsocialclub.util.Util.downloadImageToRecycler
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class LastPrivateMessageListAdapter @Inject constructor(
    var userMessage: ArrayList<PrivateMessage>,
    private val listener: Listener,
) : RecyclerView.Adapter<LastPrivateMessageListAdapter.PrivateMessageListFragmentPart>() {

    interface Listener {
        fun onItemClickListener(privateMessage: PrivateMessage)
    }

    class PrivateMessageListFragmentPart(private val binding: ChatListRawBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val timerMessage = binding.timerMessage
        val userNameForChat = binding.userNameForChat
        val lastMessage = binding.lastMessage
        val userImageLastMessage = binding.userImageLastMessage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrivateMessageListFragmentPart {
        val binding = ChatListRawBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PrivateMessageListFragmentPart(binding)
    }

    override fun onBindViewHolder(holder: PrivateMessageListFragmentPart, position: Int) {
        if (position < userMessage.size && position < userMessage.size) {
            val lastMessage = userMessage[position]

            // currentTime Taking
            val currentTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val savedTime = lastMessage.timestamp // Kaydedilen zaman
            val savedDate = dateFormat.parse(savedTime)
            val timeDifferenceInMillis = currentTime.time - savedDate!!.time
            val hoursDifference = timeDifferenceInMillis / (1000 * 60 * 60) // Saat cinsinden fark

            if (hoursDifference in 1..23) {
                holder.timerMessage.text = lastMessage.timestamp.substring(11, 16)
            }
            if (hoursDifference > 24) {
                holder.timerMessage.text = "Dün"
            }
            if (hoursDifference > 48) {
                holder.timerMessage.text = lastMessage.timestamp.substring(5, 11)
            }

            holder.timerMessage.text = lastMessage.timestamp
            holder.userNameForChat.text = lastMessage.chatUser
            holder.lastMessage.text = lastMessage.message
            holder.userImageLastMessage.downloadImageToRecycler(
                lastMessage.fromUUID,
                createPlaceHolder(PetSocialClubApplication.applicationContext())
            )
            holder.timerMessage.text = lastMessage.toUUID

            holder.itemView.setOnClickListener {
                listener.onItemClickListener(lastMessage)
            }
        }
    }

    override fun getItemCount(): Int {
        return userMessage.size
    }
}
