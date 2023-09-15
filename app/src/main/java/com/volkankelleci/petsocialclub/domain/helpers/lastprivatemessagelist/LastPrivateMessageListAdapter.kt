package com.volkankelleci.petsocialclub.domain.helpers.lastprivatemessagelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.data.PrivateMessage
import com.volkankelleci.petsocialclub.util.Util.createPlaceHolder
import com.volkankelleci.petsocialclub.util.Util.downloadImageToRecycler
import kotlinx.android.synthetic.main.chat_list_raw.view.lastMessage
import kotlinx.android.synthetic.main.chat_list_raw.view.timerMessage
import kotlinx.android.synthetic.main.chat_list_raw.view.userImageLastMessage
import kotlinx.android.synthetic.main.chat_list_raw.view.userNameForChat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class LastPrivateMessageListAdapter(
    var userMessage:ArrayList<PrivateMessage>,
    val listener: Listener,
    private val context: Context,
    ): RecyclerView.Adapter<LastPrivateMessageListAdapter.PrivateMessageListFragmentPart>() {

    interface Listener{
        fun onItemClickListener(privateMessage: PrivateMessage)
    }
    class PrivateMessageListFragmentPart(itemView: View):RecyclerView.ViewHolder(itemView) {
    }
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): PrivateMessageListFragmentPart {
        val itemView =LayoutInflater.from(parent.context).inflate(R.layout.chat_list_raw, parent, false)
        return PrivateMessageListFragmentPart(itemView)
    }

    override fun onBindViewHolder(holder: PrivateMessageListFragmentPart, position: Int) {
        if (position < userMessage.size && position < userMessage.size) {

           val lastMessage = userMessage[position]
            //currentTime Taking
           val currentTime = Calendar.getInstance().time
           val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
           val savedTime = lastMessage.timestamp // Kaydedilen zaman
           val savedDate = dateFormat.parse(savedTime)
           val timeDifferenceInMillis = currentTime.time - savedDate!!.time
           val hoursDifference = timeDifferenceInMillis / (1000 * 60 * 60) // Saat cinsinden fark

           if (hoursDifference>0 && hoursDifference<24){
               holder.itemView.timerMessage.text=lastMessage.timestamp.substring(11,16)
           }
            if (hoursDifference>24){
               holder.itemView.timerMessage.text="Dün"
           }
            if (hoursDifference>48){
               holder.itemView.timerMessage.text=lastMessage.timestamp.substring(5,11)
           }

            holder.itemView.timerMessage.text=lastMessage.timestamp
            holder.itemView.userNameForChat.text=userMessage[position].chatUser
            holder.itemView.lastMessage.text=userMessage[position].message
            holder.itemView.userImageLastMessage.downloadImageToRecycler(userMessage.get(position).fromUUID,
                    createPlaceHolder(context)
            )
            holder.itemView.timerMessage.text=lastMessage.toUUID



            holder.itemView.setOnClickListener {
                    listener.onItemClickListener(userMessage.get(position))

                }

        }

        }
    override fun getItemCount(): Int {

        return userMessage.size
    }


    }

