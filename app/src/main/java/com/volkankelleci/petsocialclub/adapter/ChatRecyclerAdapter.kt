package com.volkankelleci.petsocialclub.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.util.ChatData

class ChatRecyclerAdapter : RecyclerView.Adapter<ChatRecyclerAdapter.ChatRecyclerViewHolder>() {
    class ChatRecyclerViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    }
    private val diffutil=object: DiffUtil.ItemCallback<ChatData>() {
        override fun areItemsTheSame(oldItem: ChatData, newItem: ChatData): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: ChatData, newItem: ChatData): Boolean {
            return oldItem == newItem
        }
    }
    private val recyclerListDiffUtil=AsyncListDiffer(this,diffutil)

    var chats:List<ChatData>
    get() =recyclerListDiffUtil.currentList
        set(value)=recyclerListDiffUtil.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRecyclerViewHolder {

        val view=LayoutInflater.from(parent.context).inflate(R.layout.)

    }

    override fun onBindViewHolder(holder: ChatRecyclerViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return chats.size
    }
}