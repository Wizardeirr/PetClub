package com.volkankelleci.petsocialclub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.util.ChatData
import com.volkankelleci.petsocialclub.util.Util.auth
import kotlinx.android.synthetic.main.chat_recycler_raw.view.*

class ChatRecyclerAdapter(): RecyclerView.Adapter<ChatRecyclerAdapter.ChatRecyclerViewHolder>() {

    private val WRITER_USER=1
    private val ANSWER_USER=2

    class ChatRecyclerViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    }
    private val diffutil=object :DiffUtil.ItemCallback<ChatData>(){
        override fun areItemsTheSame(oldItem: ChatData, newItem: ChatData): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: ChatData, newItem: ChatData): Boolean {
            return oldItem==newItem
        }

    }
    private val recyclerDiff=AsyncListDiffer(this,diffutil)
    var chats:List<ChatData>
    get()=recyclerDiff.currentList
        set(value)=recyclerDiff.submitList(value)

    override fun getItemViewType(position: Int): Int {

        val chat=chats.get(position)
        if (chat.chatUser== auth.currentUser?.email.toString()){
            return WRITER_USER
        }else {
            return ANSWER_USER
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRecyclerViewHolder {

        if (viewType==WRITER_USER){
            val view=LayoutInflater.from(parent.context).inflate(R.layout.answer_recycler_raw,parent,false)
            return ChatRecyclerViewHolder(view)
        }else{
            val view=LayoutInflater.from(parent.context).inflate(R.layout.chat_recycler_raw,parent,false)
            return ChatRecyclerViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: ChatRecyclerViewHolder, position: Int) {
        holder.itemView.chatTV.text=chats[position].chatText


    }

    override fun getItemCount(): Int {
        return chats.size
    }



}