package com.volkankelleci.petsocialclub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.util.Post
import com.volkankelleci.petsocialclub.util.Util.createPlaceHolder
import com.volkankelleci.petsocialclub.util.Util.downloadImageToRecycler
import kotlinx.android.synthetic.main.recycler_raw.view.*

class UserRecyclerAdapter(val postList:ArrayList<Post>): RecyclerView.Adapter<UserRecyclerAdapter.UserViewHolder>() {

    class UserViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
    val inflater=LayoutInflater.from(parent.context)
        val viewHolder=inflater.inflate(R.layout.recycler_raw,parent,false)
        return UserViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.titleRecycler.text=postList[position].userTitle
        holder.itemView.commentRecycler.text=postList[position].userComment
        holder.itemView.petImageRecycler.downloadImageToRecycler(postList[position].userImage,
            createPlaceHolder(holder.itemView.context))
    }

    override fun getItemCount(): Int {
        return postList.size
    }

}