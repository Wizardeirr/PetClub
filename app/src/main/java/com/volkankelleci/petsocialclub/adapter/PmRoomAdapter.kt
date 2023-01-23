package com.volkankelleci.petsocialclub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.util.ChatData
import com.volkankelleci.petsocialclub.util.PrivateMessage
import com.volkankelleci.petsocialclub.util.Util
import kotlinx.android.synthetic.main.pm_raw.view.*

class PmRoomAdapter: RecyclerView.Adapter<PmRoomAdapter.PmRoomAdapterViewHolder>() {
    class PmRoomAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
    private val WRITER_USER = 1
    private val ANSWER_USER = 2


    private val diffutil = object : DiffUtil.ItemCallback<ChatData>() {
        override fun areItemsTheSame(oldItem: ChatData, newItem: ChatData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ChatData, newItem: ChatData): Boolean {
            return oldItem == newItem
        }

    }
    private val recyclerDiff = AsyncListDiffer(this, diffutil)
    var chats: MutableList<ChatData>
        get() = recyclerDiff.currentList
        set(value) = recyclerDiff.submitList(value)

    override fun getItemViewType(position: Int): Int {

        val chat = chats.get(position)
        if (chat.chatUser == Util.auth.currentUser?.email.toString()) {
            return WRITER_USER
        } else {
            return ANSWER_USER
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PmRoomAdapterViewHolder {

        if (viewType == WRITER_USER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.answer_recycler_raw, parent, false)
            return PmRoomAdapterViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.chat_recycler_raw, parent, false)
            return PmRoomAdapterViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: PmRoomAdapterViewHolder, position: Int) {
        holder.itemView.privateMessageChatTV.text="chats.get(position).chatText"
    }

    override fun getItemCount(): Int {
        return chats.size
    }
}