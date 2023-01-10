package com.volkankelleci.petsocialclub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.util.ChatData
import com.volkankelleci.petsocialclub.util.Post
import kotlinx.android.synthetic.main.message_raw.view.*

class ChatRecyclerAdapter (val chatList:ArrayList<ChatData>): RecyclerView.Adapter<ChatRecyclerAdapter.ChatRecyclerViewHolder>() {
    class ChatRecyclerViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRecyclerViewHolder {

        val view=LayoutInflater.from(parent.context).inflate(R.layout.message_raw,parent,false)
        return ChatRecyclerViewHolder(view)


    }

    override fun onBindViewHolder(holder: ChatRecyclerViewHolder, position: Int) {
    holder.itemView.answerMessageRawText.text=chatList[position].chatText

    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}