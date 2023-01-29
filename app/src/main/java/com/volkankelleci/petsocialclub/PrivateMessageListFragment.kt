package com.volkankelleci.petsocialclub

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_private_message_list.*

class PrivateMessageListFragment: Fragment(R.layout.fragment_private_message_list) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabForPM.setOnClickListener {
            val action=PrivateMessageListFragmentDirections.actionPrivateMessageListFragmentToPrivateChatFragment()
            findNavController().navigate(action)
        }

    }
}