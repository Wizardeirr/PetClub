package com.volkankelleci.petsocialclub.lastprivatemessagelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.data.PrivateMessage
import com.volkankelleci.petsocialclub.util.Util.getToUUIDFromSharedPreferences
import kotlinx.android.synthetic.main.chat_list_raw.view.lastMessage

class LastPrivateMessageListAdapter(
    var userMessage:ArrayList<PrivateMessage>,
    val listener:Listener, private val context: Context
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
        val lastMessage = userMessage[position]
        holder.itemView.lastMessage.text = lastMessage.message
        holder.itemView.setOnClickListener {
            listener.onItemClickListener(userMessage[position])

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

