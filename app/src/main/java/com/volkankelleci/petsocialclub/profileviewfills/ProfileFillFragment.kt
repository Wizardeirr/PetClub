package com.volkankelleci.petsocialclub.profileviewfills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.data.UsersData
import com.volkankelleci.petsocialclub.viewmodel.ProfileFillFragmentViewModel
import kotlinx.android.synthetic.main.fragment_profile_fill.*

class ProfileFillFragment : Fragment() {
    lateinit var viewModel: ProfileFillFragmentViewModel

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
        val viewModel=ViewModelProvider(this).get(ProfileFillFragmentViewModel::class.java)
        saveButton.setOnClickListener {

            val petName=petName.text.toString()
            val petage=petAge.text.toString()
            val petSpecies=petSpecies.text.toString()
            val petWeight=petKg.text.toString()
            val petGender=petGender.text.toString()
            val vaccineInfo=petVaccine.text.toString()
            val ownersName=petOwnerName.text.toString()
            val dataInput=UsersData(petName,petage,petSpecies,petWeight,petGender,vaccineInfo,ownersName)

            viewModel.saveUser(dataInput)

            if (petName.isNotEmpty()&&petage.isNotEmpty()&&petSpecies.isNotEmpty()&&petWeight.isNotEmpty()&&petGender.isNotEmpty()
                &&vaccineInfo.isNotEmpty()&&ownersName.isNotEmpty()){
                Toast.makeText(activity, "Profile Created", Toast.LENGTH_LONG).show()
                val action=ProfileFillFragmentDirections.actionProfileFillFragmentToUsersHomeFragment()
                findNavController().navigate(action)
            }
        }
    }
}