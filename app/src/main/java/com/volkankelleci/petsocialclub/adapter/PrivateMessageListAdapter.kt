package com.volkankelleci.petsocialclub.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.util.PrivateMessage

class PrivateMessageListAdapter(var userMessage:ArrayList<PrivateMessage>): RecyclerView.Adapter<PrivateMessageListAdapter.PrivateMessageListFragmentPart>() {
    class PrivateMessageListFragmentPart(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PrivateMessageListFragmentPart {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PrivateMessageListFragmentPart, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}