package com.volkankelleci.petsocialclub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.volkankelleci.petsocialclub.data.UsersData
import com.volkankelleci.petsocialclub.util.Post
import com.volkankelleci.petsocialclub.util.UserProfileInput
import com.volkankelleci.petsocialclub.util.Util.auth
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class ProfileFragment : Fragment() {
    private val userProfile= Firebase.firestore.collection("UserProfileInfos")

    var UserProfileInfos=ArrayList<UserProfileInput>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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

        takesData()
    }
    fun takesData(){
        var database: FirebaseFirestore = FirebaseFirestore.getInstance()
        database.collection("UserProfileInfos").orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if(error!=null){
                }else
                    if (value!=null){
                        if (value.isEmpty==false){
                            val documents=value.documents
                            UserProfileInfos.clear()
                            for (document in documents){
                                document.get("Post")
                                val ownerName=document.get("ownerName").toString()
                                val petAge=document.get("petAge").toString()
                                val petGender=document.get("petGender").toString()
                                val petKg=document.get("petKg").toString()
                                val petName=document.get("petName").toString()
                                val petSpecies=document.get("petSpecies").toString()
                                val vaccineInfo=document.get("vaccineInfo").toString()

                                val downloadInfos= UserProfileInput(ownerName,petAge,petGender,petKg,petName,petSpecies,vaccineInfo)
                                UserProfileInfos.add(downloadInfos)



                            }

                        }
                    }
            }
    }

}