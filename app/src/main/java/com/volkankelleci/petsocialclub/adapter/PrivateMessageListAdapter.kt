package com.volkankelleci.petsocialclub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.util.PrivateMessage

class PrivateMessageListAdapter(var userMessage:ArrayList<PrivateMessage>): RecyclerView.Adapter<PrivateMessageListAdapter.PrivateMessageListFragmentPart>() {
    class PrivateMessageListFragmentPart(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PrivateMessageListFragmentPart {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.chat_list_raw,parent,false)
        return PrivateMessageListFragmentPart(view)
    }

    override fun onBindViewHolder(holder: PrivateMessageListFragmentPart, position: Int) {

    }

    override fun getItemCount(): Int {
        return userMessage.size
    }
}