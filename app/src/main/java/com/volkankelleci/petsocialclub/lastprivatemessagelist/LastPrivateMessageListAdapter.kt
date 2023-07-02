package com.volkankelleci.petsocialclub.lastprivatemessagelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.data.model.User
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.data.PrivateMessage
import com.volkankelleci.petsocialclub.data.Timestamp
import com.volkankelleci.petsocialclub.data.UserInfo
import com.volkankelleci.petsocialclub.util.Util.createPlaceHolder
import com.volkankelleci.petsocialclub.util.Util.downloadImageToRecycler
import com.volkankelleci.petsocialclub.util.Util.getToUUIDFromSharedPreferences
import kotlinx.android.synthetic.main.chat_list_raw.view.lastMessage
import kotlinx.android.synthetic.main.chat_list_raw.view.timerMessage
import kotlinx.android.synthetic.main.chat_list_raw.view.userImageLastMessage
import kotlinx.android.synthetic.main.chat_list_raw.view.userNameForChat
import kotlinx.android.synthetic.main.fragment_main.view.imageView
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.nanoseconds

class LastPrivateMessageListAdapter(
    var userMessage:ArrayList<PrivateMessage>,
    val listener:Listener, private val context: Context,private var userInfo:ArrayList<UserInfo>
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
        if (position < userMessage.size && position < userInfo.size) {

            val lastMessage = userMessage[position]
            val userInfo = userInfo[position]
            //currentTime Taking
            /*val currentTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val savedTime = "${lastMessage.timestamp}" // Kaydedilen zaman
            val savedDate = dateFormat.parse(savedTime)
            val timeDifferenceInMillis = currentTime.time - savedDate.time
            val hoursDifference = timeDifferenceInMillis / (1000 * 60 * 60) // Saat cinsinden fark

            println(hoursDifference)
            if (hoursDifference>0 && hoursDifference<24){
                holder.itemView.timerMessage.text=lastMessage.timestamp.substring(11,16)
            }else if (hoursDifference>24){
                holder.itemView.timerMessage.text="Dün"
            }else if (hoursDifference>48){
                holder.itemView.timerMessage.text=lastMessage.timestamp.substring(5,11)

            }

             */


                holder.itemView.lastMessage.text = lastMessage.message
                holder.itemView.userNameForChat.text=userInfo.userName
                holder.itemView.userImageLastMessage.downloadImageToRecycler(userInfo.userImage,
                    createPlaceHolder(context)
                )
                holder.itemView.setOnClickListener {
                    listener.onItemClickListener(userMessage[position])
                }

        }

        }
    override fun getItemCount(): Int {
        return userMessage.size
    }
    fun updateList(newList: ArrayList<PrivateMessage>) {
        userMessage = newList
        notifyDataSetChanged()
    }

    }

