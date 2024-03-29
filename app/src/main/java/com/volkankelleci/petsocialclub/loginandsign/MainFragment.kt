package com.volkankelleci.petsocialclub.loginandsign

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.volkankelleci.petsocialclub.util.BaseViewBindingFragment
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.databinding.FragmentMainBinding
import com.volkankelleci.petsocialclub.util.Util.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainFragment : BaseViewBindingFragment<FragmentMainBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater,container,false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Google Signing Opt


        binding.googleSign.setOnClickListener {
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
        binding.signUpText.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToSignUpFragment()
            findNavController().navigate(action)
        }
        binding.loginButton.setOnClickListener {
            loginUser()
        }

    }
    override fun onStart() {
        super.onStart()
        checkLoggedInState()
    }
    fun loginUser() {
        val email = binding.userLog.text.toString().trim()
        val password = binding.passwordLog.text.toString().trim()


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

        } else {
            val action=MainFragmentDirections.actionMainFragmentToUsersHomeFragment()
            findNavController().navigate(action)
            Toast.makeText(context, "SIGN DONE", Toast.LENGTH_LONG).show()

        }

    }

    private fun googleAuthForFireBase(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        CoroutineScope(Dispatchers.IO).launch {
            try {

                auth.signInWithCredential(credentials).await()
                withContext(Dispatchers.Main) {
                    checkLoggedInState()

                // Toast.makeText(context, "Successfully", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
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