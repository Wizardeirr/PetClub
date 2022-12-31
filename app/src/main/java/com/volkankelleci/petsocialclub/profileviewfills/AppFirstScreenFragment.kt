package com.volkankelleci.petsocialclub.profileviewfills

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.databinding.FragmentAppFirstScreenBinding

import com.volkankelleci.petsocialclub.util.Util.auth
import com.volkankelleci.petsocialclub.viewmodel.ProfileFillFragmentViewModel
import kotlinx.android.synthetic.main.fragment_app_first_screen.*


class AppFirstScreenFragment : Fragment() {
    private var _binding: FragmentAppFirstScreenBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel:ProfileFillFragmentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val viewModel = ViewModelProvider(this).get(ProfileFillFragmentViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAppFirstScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)



    }


}





