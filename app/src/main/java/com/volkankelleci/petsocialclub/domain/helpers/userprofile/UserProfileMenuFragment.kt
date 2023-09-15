package com.volkankelleci.petsocialclub.domain.helpers.userprofile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.volkankelleci.petsocialclub.databinding.FragmentUserProfileMenuBinding
import com.volkankelleci.petsocialclub.data.UserInfo
import com.volkankelleci.petsocialclub.util.Util
import com.volkankelleci.petsocialclub.util.Util.auth
import com.volkankelleci.petsocialclub.util.Util.createPlaceHolder
import com.volkankelleci.petsocialclub.util.Util.downloadImageToRecycler
import kotlinx.android.synthetic.main.fragment_user_profile_menu.*

class UserProfileMenuFragment : Fragment() {
    private var _binding: FragmentUserProfileMenuBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserProfileMenuBinding.inflate(inflater, container, false)
        val view=binding.root
        return view
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Util.database.collection("userProfileInfo")
            .addSnapshotListener { value, error ->
                if (error != null) {
                } else
                    if (value != null) {
                        if (value.isEmpty == false) {
                            val documents = value.documents
                            for (document in documents) {
                                document.get("userProfileInfo")

                                if (auth.currentUser!!.uid==document.get("userUUID").toString()) {
                                    val userEmail = document.get("userEmail").toString()
                                    val userUUID = document.get("userUUID").toString()
                                    val userName = document.get("userName").toString()
                                    val userPetName = document.get("petName").toString()
                                    val userImage = document.get("userImage").toString()
                                    val userPassword=document.get("password").toString()
                                    val downloadInfos = UserInfo(userUUID, userEmail, userName, userPetName, userImage,userPassword)
                                    binding.userNameETProfile.text="Username:  ${downloadInfos.userName}"
                                    binding.userSignEmail.text="E-mail:  ${downloadInfos.userEmail}"
                                    binding.petNameProfile.text="Pet Name:  ${downloadInfos.petName}"
                                    binding.passwordSignProfile.text= downloadInfos.userPassword
                                    binding.signImageViewProfile.downloadImageToRecycler(downloadInfos.userImage,
                                        createPlaceHolder(requireContext()))
                                }
                                }

                        }
                    }
    }
    }
}