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
import com.volkankelleci.petsocialclub.util.Util.getToUUIDFromSharedPreferences
import kotlinx.android.synthetic.main.chat_list_raw.view.lastMessage
import kotlinx.android.synthetic.main.chat_list_raw.view.timerMessage

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
            val userInfo=userInfo[position]
            holder.itemView.lastMessage.text = lastMessage.message
            holder.itemView.timerMessage.text=lastMessage.timestamp
            holder.itemView.setOnClickListener {
                listener.onItemClickListener(userMessage[position])

            }
        }

        }
    override fun getItemCount(): Int {
        return userInfo.size
    }
    fun updateList(newList: ArrayList<PrivateMessage>) {
        userMessage = newList
        notifyDataSetChanged()
    }

    }

