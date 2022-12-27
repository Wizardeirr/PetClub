package com.volkankelleci.petsocialclub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class MainFragment : Fragment() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        auth = FirebaseAuth.getInstance()

        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Google Signing Opt

        googleSign.setOnClickListener {
            val options=GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.webclient_id))
                .requestEmail()
                .build()

        }

        //Default Signing
        signUpText.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToSignUpFragment()
            findNavController().navigate(action)
        }
        loginButton.setOnClickListener {
            loginUser()
        }
    }

    override fun onStart() {
        super.onStart()
        checkLoggedInState()
    }

    fun loginUser() {
        val email = userLog.text.toString()
        val password = passwordSign.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        checkLoggedInState()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()

                }
            }
        }
    }

    private fun checkLoggedInState() {
        if (auth.currentUser == null) {
            Toast.makeText(context, "SIGN IS UNSUCCESS", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "SIGN DONE", Toast.LENGTH_LONG).show()
        }

    }



}