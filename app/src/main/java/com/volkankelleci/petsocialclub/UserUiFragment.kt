package com.volkankelleci.petsocialclub

import android.os.Bundle
import android.os.Message
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.volkankelleci.petsocialclub.util.Util.auth
import com.volkankelleci.petsocialclub.databinding.FragmentUserUiBinding
import kotlinx.android.synthetic.main.fragment_user_ui.*

class UserUiFragment : Fragment() {

    private var binding: FragmentUserUiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)
        binding = FragmentUserUiBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        requireActivity().menuInflater.inflate(R.menu.user_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logOutOnMenu) {
            try {
                auth.signOut()
                Toast.makeText(activity, "Sign Out Successfully", Toast.LENGTH_SHORT).show()
                val action = UserUiFragmentDirections.actionUserUiFragmentToMainFragment()
                findNavController().navigate(action)

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)

    }





}