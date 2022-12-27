package com.volkankelleci.petsocialclub

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.volkankelleci.Util.auth
import com.volkankelleci.petsocialclub.databinding.FragmentUserUiBinding

class UserUiFragment : Fragment() {


    private var binding:FragmentUserUiBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentUserUiBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
         super.onCreateOptionsMenu(menu, inflater)
        requireActivity().menuInflater.inflate(R.menu.user_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.logOutOnMenu){
            try {
                auth.signOut()
                Toast.makeText(activity, "Sign Out Successfully", Toast.LENGTH_SHORT).show()
                val action=UserUiFragmentDirections.actionUserUiFragmentToMainFragment()
                findNavController().navigate(action)

            }catch (e:Exception){
                e.printStackTrace()
                Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)

    }
}