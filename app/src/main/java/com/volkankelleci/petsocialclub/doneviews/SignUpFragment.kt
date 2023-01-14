package com.volkankelleci.petsocialclub.doneviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.databinding.FragmentSignUpBinding
import com.volkankelleci.petsocialclub.util.Util
import com.volkankelleci.petsocialclub.util.Util.database

import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_user_chat.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.sign

class SignUpFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var _binding:FragmentSignUpBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore = Firebase.firestore
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        _binding=FragmentSignUpBinding.inflate(inflater,container,false)
        val view=binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpButton.setOnClickListener {
            signUser()

            Toast.makeText(context, "Sign is Success", Toast.LENGTH_LONG).show()
            val action = SignUpFragmentDirections.actionSignUpFragmentToMainFragment()
            findNavController().navigate(action)

        }

    }
    fun signUser() {

        val email = userSign.text.toString()
        val password = passwordSign.text.toString()



        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {

                    auth.createUserWithEmailAndPassword(email, password).await()

                    withContext(Dispatchers.Main) {
                        userInfoTake()
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
    private fun userInfoTake(){
        val userUUID= Util.auth.currentUser!!.uid
        val userEmail= Util.auth.currentUser!!.email.toString()
        val userName= binding.userNameET.text.toString()

        val userInfoMap = HashMap<String, Any>()

        userInfoMap.put("userUUID", userUUID)
        userInfoMap.put("userEmail", userEmail)
        userInfoMap.put("ffff", userName)


        database.collection("userProfileInfo").add(userInfoMap).addOnSuccessListener {
            Toast.makeText(requireContext(), "UUID TOOK", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

        }
    }


}