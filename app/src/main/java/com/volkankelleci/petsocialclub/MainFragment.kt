package com.volkankelleci.petsocialclub

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
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
            val options = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.webclient_id))
                .requestEmail()
                .build()
            val signer = GoogleSignIn.getClient(requireActivity(), options)
            signer.signInIntent.also {
                startActivityForResult(it, 0)
            }


        }

        //Default Signing
        signUpText.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToSignUpFragment()
            findNavController().navigate(action)
        }
        loginButton.setOnClickListener {
            loginUser()
            val action=MainFragmentDirections.actionMainFragmentToUserUiFragment()
            findNavController().navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()
        checkLoggedInState()
    }

    fun loginUser() {
        val email = userLog.text.toString()
        val password = passwordLog.text.toString()

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

    private fun googleAuthForFireBase(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        CoroutineScope(Dispatchers.IO).launch {
            try {

                auth.signInWithCredential(credentials).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Successfully", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != 0) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
            account?.let {
                googleAuthForFireBase(it)
            }
        }
    }


}