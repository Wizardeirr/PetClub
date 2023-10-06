package com.volkankelleci.petsocialclub.domain.helpers.userslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.data.UserInfo
import com.volkankelleci.petsocialclub.util.Util
import com.volkankelleci.petsocialclub.util.Util.downloadImageToRecycler
import kotlinx.android.synthetic.main.private_chat_raw.view.userEmail
import kotlinx.android.synthetic.main.private_chat_raw.view.userImage
import kotlinx.android.synthetic.main.private_chat_raw.view.userPetName
import kotlinx.android.synthetic.main.private_chat_raw.view.userUUID
import javax.inject.Inject

class UserListAdapter @Inject constructor(
    val userList: ArrayList<UserInfo>,
    ) : RecyclerView.Adapter<UserListAdapter.PrivateChatAdapterViewHolder>() {
    private var selectedUUID: String = ""

    class PrivateChatAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PrivateChatAdapterViewHolder {
        val itemView =LayoutInflater.from(parent.context).inflate(R.layout.private_chat_raw, parent, false)


        return PrivateChatAdapterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PrivateChatAdapterViewHolder, position: Int) {
        holder.itemView.userUUID.text = userList[position].uuid
        holder.itemView.userEmail.text = userList[position].userName
        holder.itemView.userPetName.text = userList[position].petName

        // navigate part
        holder.itemView.setOnClickListener {
            val action =UserListFragmentDirections.actionUserListFragmentToPmRoomFragment(userList.get(position).userName, userList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
            println(userList[position].uuid)
        }
        selectedUUID = userList[position].uuid



        // image with glide
        holder.itemView.userImage.downloadImageToRecycler(userList[position].userImage,
            Util.createPlaceHolder(holder.itemView.context))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}