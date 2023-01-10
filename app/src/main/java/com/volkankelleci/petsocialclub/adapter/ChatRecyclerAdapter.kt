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
import kotlinx.android.synthetic.main.answer_message_raw.view.*

class ChatRecyclerAdapter : RecyclerView.Adapter<ChatRecyclerAdapter.ChatRecyclerViewHolder>() {
    class ChatRecyclerViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    }
    private val diffutil=object: DiffUtil.ItemCallback<ChatData>() {
        override fun areItemsTheSame(oldItem: ChatData, newItem: ChatData): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: ChatData, newItem: ChatData): Boolean {
            return oldItem==newItem
        }
    }
    private val recyclerListDiffUtil=AsyncListDiffer(this,diffutil)

    var chats:List<ChatData>
    get() =recyclerListDiffUtil.currentList
        set(value)=recyclerListDiffUtil.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRecyclerViewHolder {

        val inflater=LayoutInflater.from(parent.context)
        val viewHolder=inflater.inflate(R.layout.answer_message_raw,parent,false)
        return ChatRecyclerViewHolder(viewHolder)


    }

    override fun onBindViewHolder(holder: ChatRecyclerViewHolder, position: Int) {
       val textView=holder.itemView.findViewById<TextView>(R.id.messageRawText)
        textView.text="${chats[position].chatText}"

    }

    override fun getItemCount(): Int {
        return chats.size
    }
}