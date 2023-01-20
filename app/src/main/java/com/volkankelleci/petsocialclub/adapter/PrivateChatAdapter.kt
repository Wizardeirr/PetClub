package com.volkankelleci.petsocialclub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.volkankelleci.petsocialclub.PrivateChatFragmentDirections
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.util.UserInfo
import com.volkankelleci.petsocialclub.util.Util
import com.volkankelleci.petsocialclub.util.Util.downloadImageToRecycler
import kotlinx.android.synthetic.main.private_chat_raw.view.*
import kotlinx.android.synthetic.main.recycler_raw.view.*

class PrivateChatAdapter(val userList:ArrayList<UserInfo>): RecyclerView.Adapter<PrivateChatAdapter.PrivateChatAdapterViewHolder>() {
    class PrivateChatAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PrivateChatAdapterViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.private_chat_raw,parent,false)
        return PrivateChatAdapterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PrivateChatAdapterViewHolder, position: Int) {
        holder.itemView.userUUID.text=userList[position].uuid
        holder.itemView.userEmail.text=userList[position].userName
        holder.itemView.userPetName.text=userList[position].petName

        holder.itemView.userList.setOnClickListener {
            val save=userList.get(position).uuid

            val action=PrivateChatFragmentDirections.actionPrivateChatFragmentToPrivateChatFragmentRoom(save)
            Navigation.findNavController(it).navigate(action)
        }

        holder.itemView.userImage.downloadImageToRecycler(userList[position].userImage,
            Util.createPlaceHolder(holder.itemView.context))




    }

    override fun getItemCount(): Int {

        return userList.size
    }
}