package com.volkankelleci.petsocialclub

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.volkankelleci.petsocialclub.util.Util

class UsersHomeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users_home, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        requireActivity().menuInflater.inflate(R.menu.right_top_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logOutButton) {
            try {
                Util.auth.signOut()
                Toast.makeText(activity, "Sign Out Successfully", Toast.LENGTH_SHORT).show()
                val action =UsersHomeFragmentDirections.actionUsersHomeFragmentToMainFragment()
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
        val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavView)
        navBar.visibility=View.VISIBLE
    }
}