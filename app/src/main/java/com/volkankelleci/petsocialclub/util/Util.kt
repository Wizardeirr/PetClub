package com.volkankelleci.petsocialclub.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.volkankelleci.petsocialclub.R
import de.hdodenhof.circleimageview.CircleImageView

object Util {
     var auth: FirebaseAuth= FirebaseAuth.getInstance()
     var storage:FirebaseStorage=FirebaseStorage.getInstance()
     @SuppressLint("StaticFieldLeak")
     var database:FirebaseFirestore=FirebaseFirestore.getInstance()


     fun ImageView.downloadImageToRecycler(url: String, placeholder:CircularProgressDrawable){
          val options=RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)
          Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
     }
     fun createPlaceHolder(context: Context):CircularProgressDrawable{
          return CircularProgressDrawable(context).apply {
               strokeWidth=8f
               centerRadius=40f
               start()
          }
     }
     @SuppressLint("MutatingSharedPrefs")
     fun saveToUUIDToSharedPreferences(context: Context, toUUID: String) {
          val sharedPrefs = context.getSharedPreferences("YourSharedPrefsName", Context.MODE_PRIVATE)
          val uuidSet = sharedPrefs.getStringSet("uuidSet", mutableSetOf()) ?: mutableSetOf()
          uuidSet.add(toUUID)
          sharedPrefs.edit().putStringSet("uuidSet", uuidSet).apply()
     }

     val homeFragmentTitle="PetSocialClub"
}