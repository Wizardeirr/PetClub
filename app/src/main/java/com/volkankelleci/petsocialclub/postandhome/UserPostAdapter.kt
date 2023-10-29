package com.volkankelleci.petsocialclub.postandhome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.data.Post
import com.volkankelleci.petsocialclub.data.UserInfo
import com.volkankelleci.petsocialclub.databinding.RecyclerRawBinding // ViewBinding ekledik
import com.volkankelleci.petsocialclub.util.Util.createPlaceHolder
import com.volkankelleci.petsocialclub.util.Util.downloadImageToRecycler
import javax.inject.Inject

class UserPostAdapter @Inject constructor(
    var postList: ArrayList<Post>,
    var pp: ArrayList<UserInfo>
) : RecyclerView.Adapter<UserPostAdapter.UserViewHolder>() {

    class UserViewHolder(binding: RecyclerRawBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleRecycler = binding.titleRecycler
        val commentRecycler = binding.commentRecycler
        val ownersName = binding.ownersName
        val postPP = binding.postPP
        val petImageRecycler = binding.petImageRecycler
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerRawBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        if (position < postList.size && position < pp.size) {
            holder.titleRecycler.text = postList[position].userTitle
            holder.commentRecycler.text = postList[position].userComment
            holder.ownersName.text = pp[position].userName
            holder.postPP.downloadImageToRecycler(pp[position].userImage, createPlaceHolder(holder.itemView.context))

            // image with glide
            holder.petImageRecycler.downloadImageToRecycler(postList[position].userImage, createPlaceHolder(holder.itemView.context))
            holder.postPP.downloadImageToRecycler(pp[position].userImage, createPlaceHolder(holder.itemView.context))
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}
