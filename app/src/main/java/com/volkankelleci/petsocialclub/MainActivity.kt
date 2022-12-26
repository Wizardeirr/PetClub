package com.volkankelleci.petsocialclub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth=FirebaseAuth.getInstance()

    }
    fun signUser(){
        val email=etUserName.text.toString()
        val password=etPassword.text.toString()
        auth.signInWithEmailAndPassword(email, password)
        if (email.isNotEmpty() && password.isNotEmpty()){

        }


    }
}