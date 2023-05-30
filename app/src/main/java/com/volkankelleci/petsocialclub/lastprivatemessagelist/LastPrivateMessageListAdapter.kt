package com.volkankelleci.petsocialclub.lastprivatemessagelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.data.PrivateMessage
import kotlinx.android.synthetic.main.chat_list_raw.view.*

class LastPrivateMessageListAdapter(var userMessage:ArrayList<PrivateMessage>,
                                    val listener:Listener,
                                    ): RecyclerView.Adapter<LastPrivateMessageListAdapter.PrivateMessageListFragmentPart>() {
    private var toUUID: String? = null
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


        val datas=userMessage[position]
        holder.itemView.userNameForChat.text=datas.chatUser
        holder.itemView.lastMessage.text=datas.message
        holder.itemView.setOnClickListener{
            listener.onItemClickListener(datas)
            val action = LastPrivateMessageListFragmentDirections.actionLastPrivateMessageListFragmentToPmRoomFragment(datas.chatUser
            ,datas.toUUID)
            println(datas.toUUID)
            val sharedPreferences = holder.itemView.context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("toUUID", datas.toUUID)
            editor.apply()
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
    fun setToUUID(toUUID: String) {
        this.toUUID = toUUID
    }


}