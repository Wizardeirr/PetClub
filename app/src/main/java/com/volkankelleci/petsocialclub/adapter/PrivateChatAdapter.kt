package com.volkankelleci.petsocialclub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.util.UserInfo
import kotlinx.android.synthetic.main.private_chat_raw.view.*

class PrivateChatAdapter(val userList:ArrayList<UserInfo>): RecyclerView.Adapter<PrivateChatAdapter.PrivateChatAdapterViewHolder>() {
    class PrivateChatAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PrivateChatAdapterViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.private_chat_raw,parent,false)
        return PrivateChatAdapterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PrivateChatAdapterViewHolder, position: Int) {
        holder.itemView.userEmail.text=userList[position].userName
        holder.itemView.userUUID.text=userList[position].uuid



    }

    override fun getItemCount(): Int {

        return userList.size
    }
}