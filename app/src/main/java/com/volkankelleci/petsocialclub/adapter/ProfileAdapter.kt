package com.volkankelleci.petsocialclub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.util.UserProfileInput
import kotlinx.android.synthetic.main.profile_raw.view.*

class ProfileAdapter(var userList: ArrayList<UserProfileInput>) :
    RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {
    class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = inflater.inflate(R.layout.profile_raw, parent, false)
        return ProfileViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {

        holder.itemView.getOwnerName.text = userList[position].userOwnerName
        holder.itemView.petAge.text = userList[position].userPetAge
        holder.itemView.petName.text = userList[position].userPetName
        holder.itemView.petGender.text = userList[position].userPetGender
        holder.itemView.petKg.text = userList[position].userPetKg
        holder.itemView.petSpecies.text = userList[position].userPetSpecies
        holder.itemView.petVaccine.text = userList[position].userVaccineInfo
        //image will add


    }

    override fun getItemCount(): Int {
        return userList.size
    }
}