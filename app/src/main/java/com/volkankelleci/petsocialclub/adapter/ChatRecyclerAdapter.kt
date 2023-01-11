package com.volkankelleci.petsocialclub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.util.ChatData
import kotlinx.android.synthetic.main.chat_recycler_raw.view.*

class ChatRecyclerAdapter (val chatList:ArrayList<ChatData>): RecyclerView.Adapter<ChatRecyclerAdapter.ChatRecyclerViewHolder>() {
    class ChatRecyclerViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRecyclerViewHolder {

        val view=LayoutInflater.from(parent.context).inflate(R.layout.chat_recycler_raw,parent,false)
        return ChatRecyclerViewHolder(view)


    }

    override fun onBindViewHolder(holder: ChatRecyclerViewHolder, position: Int) {
        holder.itemView.chatTV.text=chatList[position].chatText
        holder.itemView.dateTV.text=chatList[position].timeDate.toString()



    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}