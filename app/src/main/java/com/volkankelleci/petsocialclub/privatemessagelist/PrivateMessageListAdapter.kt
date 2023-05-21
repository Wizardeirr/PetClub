package com.volkankelleci.petsocialclub.privatemessagelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.data.PrivateMessage
import com.volkankelleci.petsocialclub.data.UserInfo
import com.volkankelleci.petsocialclub.userslist.UserListAdapter
import com.volkankelleci.petsocialclub.userslist.UserListFragmentDirections
import kotlinx.android.synthetic.main.chat_list_raw.view.*

class PrivateMessageListAdapter(var userMessage:ArrayList<PrivateMessage>,
                                val listener:Listener): RecyclerView.Adapter<PrivateMessageListAdapter.PrivateMessageListFragmentPart>() {

    interface Listener{
        fun onItemClickListener(privateMessage: PrivateMessage)
    }
    class PrivateMessageListFragmentPart(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): PrivateMessageListFragmentPart {
        val inflater=LayoutInflater.from(parent.context)
        val viewHolder=inflater.inflate(R.layout.chat_list_raw,parent,false)
        return PrivateMessageListFragmentPart(viewHolder)
    }

    override fun onBindViewHolder(holder: PrivateMessageListFragmentPart, position: Int) {

        holder.itemView.userNameForChat.text=userMessage[position].chatUser
        holder.itemView.lastMessage.text=userMessage[position].message
        holder.itemView.setOnClickListener{
            listener.onItemClickListener(userMessage.get(position))
            val action = PrivateMessageListFragmentDirections.actionPrivateMessageListFragmentToPmRoomFragment(userMessage.get(position).toUUID, userMessage.get(position).toUUID)
            Navigation.findNavController(it).navigate(action)
        }



    }

    override fun getItemCount(): Int {
        return userMessage.size
    }

    fun updateList(newList: ArrayList<PrivateMessage>) {
        userMessage.clear()
        userMessage.addAll(newList)
        notifyDataSetChanged()
    }
}