package com.volkankelleci.petsocialclub.doneviews

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.generalchatroom.GeneralChatRoom
import com.volkankelleci.petsocialclub.loginandsign.MainFragment
import com.volkankelleci.petsocialclub.privatemessagelist.PrivateMessageListFragment
import com.volkankelleci.petsocialclub.privatemessagelist.PrivateMessageListFragmentDirections
import com.volkankelleci.petsocialclub.util.Util
import com.volkankelleci.petsocialclub.util.Util.auth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        controller()

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
                    try {
                        auth.signOut()
                        Toast.makeText(this, "Sign Out Successfully", Toast.LENGTH_SHORT).show()
                        bottomNavigationView.visibility= View.GONE
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show()
                    }
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
    private fun controller(){
        if (auth.currentUser!=null){
            jumpFragment(UsersHomeFragment())
            bottomNavigationView.visibility= View.VISIBLE


        }
        else{
            jumpFragment(MainFragment())
            bottomNavigationView.visibility= View.INVISIBLE

        }
    }
    }
