package com.volkankelleci.petsocialclub.pm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.volkankelleci.petsocialclub.data.PrivateMessage
import com.volkankelleci.petsocialclub.databinding.PmAnswerRoomBinding
import com.volkankelleci.petsocialclub.databinding.PmRawBinding
import javax.inject.Inject


class PmRoomAdapter @Inject constructor(
    var auth: FirebaseAuth
) : RecyclerView.Adapter<PmRoomAdapter.PmRoomAdapterViewHolder>() {

    private val WRITER_USER = 1
    private val ANSWER_USER = 2

    class PmRoomAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
    private val diffutil = object : DiffUtil.ItemCallback<PrivateMessage>() {

        override fun areItemsTheSame(oldItem: PrivateMessage, newItem: PrivateMessage): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PrivateMessage, newItem: PrivateMessage): Boolean {
            return oldItem == newItem
        }


    }
    private val recyclerDiff = AsyncListDiffer(this, diffutil)
    var privateChats: List<PrivateMessage>
        get() = recyclerDiff.currentList
        set(value) = recyclerDiff.submitList(value)

    override fun getItemViewType(position: Int): Int {

        val chat = privateChats.get(position)
        if (chat.fromUUID == auth.currentUser?.uid.toString()) {
            return WRITER_USER
        } else {
            return ANSWER_USER
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PmRoomAdapterViewHolder {

        if (viewType == WRITER_USER) {
            val binding = PmRawBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PmRoomAdapterViewHolder(binding.root)
        } else {
            val binding = PmAnswerRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PmRoomAdapterViewHolder(binding.root)
        }

    }

    override fun onBindViewHolder(holder: PmRoomAdapterViewHolder, position: Int) {
        val binding =PmRawBinding.bind(holder.itemView)
        binding.privateMessageChatTV.text = privateChats.get(position).message

    }

    override fun getItemCount(): Int {
        return privateChats.size
    }


}