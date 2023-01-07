package com.volkankelleci.petsocialclub.profileviewfills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.data.UsersData
import kotlinx.android.synthetic.main.fragment_profile_fill.*
import kotlinx.android.synthetic.main.fragment_profile_fill.view.*
import kotlinx.android.synthetic.main.fragment_user_chat.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProfileFillFragment : Fragment() {

    private val userProfile= Firebase.firestore.collection("UserProfileInfos")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_fill, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveButton.setOnClickListener {
            val petName=petName.text.toString()
            val petage=petAge.text.toString()
            val petSpecies=petSpecies.text.toString()
            val petWeight=petKg.text.toString()
            val petGender=petGender.text.toString()
            val vaccineInfo=petVaccine.text.toString()
            val ownersName=petOwnerName.text.toString()
            val petImages=petImage.toString()
            val dataInput=UsersData(petName,petage,petSpecies,petWeight,petGender,vaccineInfo,ownersName,petImages)


            saveUser(dataInput)

            if (petName.isNotEmpty()&&petage.isNotEmpty()&&petSpecies.isNotEmpty()&&petWeight.isNotEmpty()&&petGender.isNotEmpty()
                &&vaccineInfo.isNotEmpty()&&ownersName.isNotEmpty()){
                Toast.makeText(activity, "Profile Created", Toast.LENGTH_LONG).show()
                val action=ProfileFillFragmentDirections.actionProfileFillFragmentToUsersHomeFragment()
                findNavController().navigate(action)
            }
        }
    }
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