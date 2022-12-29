package com.volkankelleci.petsocialclub.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.volkankelleci.petsocialclub.data.UsersData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class ProfileFillFragmentViewModel: ViewModel(){
    private val userProfile= Firebase.firestore.collection("UserProfileInfos")

     fun saveUser(user: UsersData)= CoroutineScope(Dispatchers.IO).launch {
        try {
            userProfile.add(user).await()
            withContext(Dispatchers.Main){

            }

        }catch (e:Exception){
            withContext(Dispatchers.Main){
                e.printStackTrace()
            }
        }
    }

}

