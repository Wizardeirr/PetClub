package com.volkankelleci.petsocialclub.userslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.data.UserInfo
import com.volkankelleci.petsocialclub.databinding.PrivateChatRawBinding
import com.volkankelleci.petsocialclub.util.Util
import com.volkankelleci.petsocialclub.util.Util.downloadImageToRecycler
import javax.inject.Inject

class UserListAdapter @Inject constructor(
    val userList: ArrayList<UserInfo>,
) : RecyclerView.Adapter<UserListAdapter.PrivateChatAdapterViewHolder>() {
    private var selectedUUID: String = ""

    class PrivateChatAdapterViewHolder(binding: PrivateChatRawBinding) : RecyclerView.ViewHolder(binding.root) {
        val userUUID=binding.userUUID
        val userEmail=binding.userEmail
        val userPetName=binding.userPetName
        val userImage=binding.userImage
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PrivateChatAdapterViewHolder {
        val itemView =PrivateChatRawBinding.inflate(LayoutInflater.from(parent.context),parent,false)


        return PrivateChatAdapterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PrivateChatAdapterViewHolder, position: Int) {
        holder.userUUID.text = userList[position].uuid
        holder.userEmail.text = userList[position].userName
        holder.userPetName.text = userList[position].petName

        // navigate part
        holder.itemView.setOnClickListener {
            val action = UserListFragmentDirections.actionUserListFragmentToPmRoomFragment(
                userList.get(position).userName, userList.get(position).uuid
            )
            Navigation.findNavController(it).navigate(action)
            println(userList[position].uuid)
        }
        selectedUUID = userList[position].uuid


        // image with glide
        holder.userImage.downloadImageToRecycler(
            userList[position].userImage,
            Util.createPlaceHolder(holder.itemView.context)
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}