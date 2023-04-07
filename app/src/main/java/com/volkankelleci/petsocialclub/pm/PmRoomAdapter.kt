package com.volkankelleci.petsocialclub.pm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.data.PrivateMessage
import com.volkankelleci.petsocialclub.util.Util.auth
import kotlinx.android.synthetic.main.pm_raw.view.privateMessageChatTV


class PmRoomAdapter(): RecyclerView.Adapter<PmRoomAdapter.PmRoomAdapterViewHolder>() {

    private val WRITER_USER = 1
    private val ANSWER_USER = 2
    class PmRoomAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

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
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.pm_raw, parent, false)
            return PmRoomAdapterViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.pm_answer_room, parent, false)
            return PmRoomAdapterViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: PmRoomAdapterViewHolder, position: Int) {

        // holder.itemView.privateMessageChatTV.text=privateChats[position].message

        holder.itemView.privateMessageChatTV.text=privateChats.get(position).message




}

    override fun getItemCount(): Int {
        return privateChats.size
    }


}