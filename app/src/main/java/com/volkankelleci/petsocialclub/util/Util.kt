package com.volkankelleci.petsocialclub.util

import android.content.Context
import android.media.Image
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

object Util {
     var auth: FirebaseAuth= FirebaseAuth.getInstance()
     var storage:FirebaseStorage=FirebaseStorage.getInstance()
     var database:FirebaseFirestore=FirebaseFirestore.getInstance()

     fun ImageView.downloadImageToRecycler(url:String,placeholder:CircularProgressDrawable){
          val options=RequestOptions().placeholder(placeholder)
          Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
     }
     fun createPlaceHolder(context: Context):CircularProgressDrawable{
          return CircularProgressDrawable(context).apply {
               strokeWidth=8f
               centerRadius=40f
               start()
          }

     }
}