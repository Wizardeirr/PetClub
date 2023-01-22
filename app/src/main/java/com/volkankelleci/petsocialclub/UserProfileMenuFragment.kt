package com.volkankelleci.petsocialclub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.volkankelleci.petsocialclub.adapter.UserProfileInfoAdapter
import com.volkankelleci.petsocialclub.databinding.FragmentUserProfileMenuBinding
import com.volkankelleci.petsocialclub.util.UserInfo
import com.volkankelleci.petsocialclub.util.Util
import kotlinx.android.synthetic.main.fragment_user_profile_menu.*

class UserProfileMenuFragment : Fragment() {

    private var _binding: FragmentUserProfileMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: UserProfileInfoAdapter

    var fragmentProfileInfoList = ArrayList<UserInfo>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserProfileMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        userProfileInfoRV.layoutManager = layoutManager
        adapter = UserProfileInfoAdapter(fragmentProfileInfoList)
        userProfileInfoRV.adapter = adapter

    }

    fun takesUserInfo() {
        Util.database.collection("userProfileInfo")
            .addSnapshotListener { value, error ->
                if (error != null) {
                } else
                    if (value != null) {
                        if (value.isEmpty == false) {
                            val documents = value.documents
                            fragmentProfileInfoList.clear()
                            for (document in documents) {
                                document.get("userProfileInfo")
                                val userEmail = document.get("userEmail").toString()
                                val userUUID = document.get("userUUID").toString()
                                val userName = document.get("userName").toString()
                                val userPetName = document.get("petName").toString()
                                val userImage = document.get("userImage").toString()
                                val downloadInfos =UserInfo(userUUID, userEmail, userName, userPetName, userImage)
                                fragmentProfileInfoList.add(downloadInfos)
                            }
                            adapter.notifyDataSetChanged()

                        }
                    }
            }
    }


}