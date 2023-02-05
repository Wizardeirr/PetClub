package com.volkankelleci.petsocialclub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.Hold
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.util.PrivateMessage
import com.volkankelleci.petsocialclub.util.UserInfo
import kotlinx.android.synthetic.main.chat_list_raw.view.*

class PrivateMessageListAdapter(var userMessage:ArrayList<PrivateMessage>): RecyclerView.Adapter<PrivateMessageListAdapter.PrivateMessageListFragmentPart>() {
    class PrivateMessageListFragmentPart(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PrivateMessageListFragmentPart {
        val inflater=LayoutInflater.from(parent.context)
        val viewHolder=inflater.inflate(R.layout.chat_list_raw,parent,false)
        return PrivateMessageListFragmentPart(viewHolder)
    }

    override fun onBindViewHolder(holder: PrivateMessageListFragmentPart, position: Int) {

        holder.itemView.userNameForChat.text=userMessage[position].chatUser

    }

    override fun getItemCount(): Int {
        return userMessage.size
    }
}