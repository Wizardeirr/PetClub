package com.volkankelleci.petsocialclub

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.volkankelleci.petsocialclub.navbotviews.SearchFragment
import com.volkankelleci.petsocialclub.navbotviews.UsersHomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment=UsersHomeFragment()
        val secondFragment=SearchFragment()
        val thirdFragment=MessageFragment()
        val fourthFragment=ProfileFragment()

        setCurrentFragment(firstFragment)

        bottomNavView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeButton->setCurrentFragment(firstFragment)
                R.id.searchButton->setCurrentFragment(secondFragment)
                R.id.messageButton->setCurrentFragment(thirdFragment)
                R.id.profileButton->setCurrentFragment(fourthFragment)
            }
            true
        }

    }
    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.loginConstraintID,fragment)
            commit()
        }

}
