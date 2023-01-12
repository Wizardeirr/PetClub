package com.volkankelleci.petsocialclub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.volkankelleci.petsocialclub.databinding.FragmentMessageBinding
import com.volkankelleci.petsocialclub.databinding.FragmentUserChatBinding

class PrivateChatFragment : Fragment() {

    private  var _binding:FragmentUserChatBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserChatBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
}