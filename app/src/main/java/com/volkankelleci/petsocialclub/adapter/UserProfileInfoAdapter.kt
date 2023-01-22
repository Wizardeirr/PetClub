package com.volkankelleci.petsocialclub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.util.UserInfo

class UserProfileInfoAdapter(var profileInfoList:ArrayList<UserInfo>): RecyclerView.Adapter<UserProfileInfoAdapter.UserProfileInfoViewHolder>() {
    class UserProfileInfoViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProfileInfoViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.fragment_user_profile_menu,parent,false)
        return UserProfileInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserProfileInfoViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {

        return profileInfoList.size
    }
}