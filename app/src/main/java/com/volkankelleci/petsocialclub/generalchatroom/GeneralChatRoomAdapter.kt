package com.volkankelleci.petsocialclub

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.data.ChatData
import com.volkankelleci.petsocialclub.databinding.AnswerRecyclerRawBinding // answer_recycler_raw.xml için ViewBinding
import com.volkankelleci.petsocialclub.databinding.ChatRecyclerRawBinding // chat_recycler_raw.xml için ViewBinding
import com.volkankelleci.petsocialclub.util.Util.auth
import javax.inject.Inject

class GeneralChatRoomAdapter @Inject constructor() : RecyclerView.Adapter<GeneralChatRoomAdapter.ChatRecyclerViewHolder>() {
    private val WRITER_USER = 1
    private val ANSWER_USER = 2

    class ChatRecyclerViewHolder(private val binding: AnswerRecyclerRawBinding) : RecyclerView.ViewHolder(binding.root) {

        val outgoingMessageTime = binding.outgoingMessageTime
        val outgoingMessageText = binding.outgoingMessageText
    }

    private val diffutil = object : DiffUtil.ItemCallback<ChatData>() {
        override fun areItemsTheSame(oldItem: ChatData, newItem: ChatData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ChatData, newItem: ChatData): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerDiff = AsyncListDiffer(this, diffutil)
    var chats: List<ChatData>
        get() = recyclerDiff.currentList
        set(value) = recyclerDiff.submitList(value)

    override fun getItemViewType(position: Int): Int {
        val chat = chats[position]
        if (chat.chatUser == auth.currentUser?.email.toString()) {
            return WRITER_USER
        } else {
            return ANSWER_USER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRecyclerViewHolder {
        if (viewType == WRITER_USER) {
            val binding = AnswerRecyclerRawBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ChatRecyclerViewHolder(binding)
        } else {
            val binding = ChatRecyclerRawBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ChatRecyclerViewHolder(binding)
        }
    }

    private fun ChatRecyclerViewHolder(binding: ChatRecyclerRawBinding): ChatRecyclerViewHolder {
            TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ChatRecyclerViewHolder, position: Int) {
        val chat = chats[position]
        holder.outgoingMessageTime.text = chat.chatUser
        holder.outgoingMessageText.text = chat.chatText
    }

    override fun getItemCount(): Int {
        return chats.size
    }
}
