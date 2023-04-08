package com.volkankelleci.petsocialclub.doneviews

import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.generalchatroom.GeneralChatRoom
import com.volkankelleci.petsocialclub.loginandsign.MainFragment
import com.volkankelleci.petsocialclub.privatemessagelist.PrivateMessageListFragment

class MainActivity : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        jumpFragment(UsersHomeFragment())

        bottomNav=findViewById(R.id.bottomNavigationView) as BottomNavigationView
        bottomNav.setOnItemSelectedListener{
            when(it.itemId){
                R.id.homeButton ->{
                    jumpFragment(UsersHomeFragment())
                    true
                }
                R.id.messageButton ->{
                    jumpFragment(GeneralChatRoom())
                    true
                }
                R.id.privateMessageButton ->{
                    jumpFragment(PrivateMessageListFragment())
                    true
                }
                R.id.profileButton ->{
                    jumpFragment(UserProfileMenuFragment())
                    true
                }
                R.id.logOutButton ->{
                    jumpFragment(MainFragment())
                    true
                }

                else -> {false}
            }
            }
        }
    private fun jumpFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView,fragment)
        transaction.commit()
    }
    }
