package com.volkankelleci.petsocialclub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.volkankelleci.petsocialclub.data.UsersData
import com.volkankelleci.petsocialclub.util.Util.auth
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class ProfileFragment : Fragment() {
    private val userProfile= Firebase.firestore.collection("UserProfileInfos")
    lateinit var data:UsersData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribeToInformations()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    private fun subscribeToInformations(){
        userProfile.addSnapshotListener{querySnapshot,firebaseFirestoreException->
            firebaseFirestoreException?.let {
                Toast.makeText(activity, "it.message", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            querySnapshot?.let {
                val sb=StringBuilder()
                for (document in it){
                    val person = document.toObject<UsersData>()
                    sb.append("$person\n")
                }
                profileTextView.text=sb.toString()

            }
        }

    }
    fun retrieveProfile()= CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot=userProfile.get().await()
            val sb=StringBuilder()
            for (document in querySnapshot.documents){
                val user=document.toObject<UsersData>()
                sb.append("$user\n")
            }
            withContext(Dispatchers.Main){

            }


        }catch (e:Exception){
            e.printStackTrace()

        }

    }

}