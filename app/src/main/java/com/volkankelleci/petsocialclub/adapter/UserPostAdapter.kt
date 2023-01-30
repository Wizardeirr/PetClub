package com.volkankelleci.petsocialclub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.doneviews.UsersHomeFragmentDirections
import com.volkankelleci.petsocialclub.util.Post
import com.volkankelleci.petsocialclub.util.UserInfo
import com.volkankelleci.petsocialclub.util.Util.createPlaceHolder
import com.volkankelleci.petsocialclub.util.Util.downloadImageToRecycler
import kotlinx.android.synthetic.main.recycler_raw.view.*

class UserPostAdapter(val postList:ArrayList<Post>, val pp:ArrayList<UserInfo>): RecyclerView.Adapter<UserPostAdapter.UserViewHolder>() {

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
        holder.itemView.ownersName.text=postList[position].userEmail
        holder.itemView.postPP.downloadImageToRecycler(pp.get(position).userImage,
            createPlaceHolder(holder.itemView.context))
        //navigate

        //image with glide
        holder.itemView.petImageRecycler.downloadImageToRecycler(postList[position].userImage,
            createPlaceHolder(holder.itemView.context))


    }

    override fun getItemCount(): Int {
        return postList.size
    }

}