package com.volkankelleci.petsocialclub.profileviewfills

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        requireActivity().menuInflater.inflate(R.menu.right_top_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logOutButton) {
            try {
                auth.signOut()
                Toast.makeText(activity, "Sign Out Successfully", Toast.LENGTH_SHORT).show()
                val action =AppFirstScreenFragmentDirections.actionAppFirstScreenFragmentToMainFragment()
                findNavController().navigate(action)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        skipText.setOnClickListener {
            val action =AppFirstScreenFragmentDirections.actionAppFirstScreenFragmentToProfileFillFragment()
            findNavController().navigate(action)
        }


        letsStartButton.setOnClickListener {
            val action =AppFirstScreenFragmentDirections.actionAppFirstScreenFragmentToProfileFillFragment()
            findNavController().navigate(action)

        }

    }


}





