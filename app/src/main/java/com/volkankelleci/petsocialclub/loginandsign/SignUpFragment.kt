package com.volkankelleci.petsocialclub.loginandsign

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.volkankelleci.petsocialclub.databinding.FragmentSignUpBinding
import com.volkankelleci.petsocialclub.util.Util
import com.volkankelleci.petsocialclub.util.Util.database
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_user_chat.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class SignUpFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    var selectedImage: Uri? = null
    var selectedImageURI: Bitmap? = null
    private val PERMISSION_CODE = 1
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
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpButton.setOnClickListener {

            val alertMessage=AlertDialog.Builder(activity)
            alertMessage.setTitle("Girilen bilgilerle devam etmek istiyor musunuz ?")
            alertMessage.setPositiveButton("No",DialogInterface.OnClickListener { dialog, which ->
                alertMessage.setMessage("Please check your informations")
            })
            alertMessage.setNegativeButton("Yes",DialogInterface.OnClickListener { dialog, which ->
                alertMessage.setMessage("You are continuing")
                val userEmail = userSign.text.toString().trim().replace("","")
                val userName = binding.userNameET.text.toString().trim().replace("","")
                val petName = binding.petName.text.toString().trim().replace("","")
                val password=binding.passwordSign.text.toString().trim().replace("","")
                if (userName.isNotEmpty()&&userEmail.isNotEmpty()&&petName.isNotEmpty()&&password.isNotEmpty()){

// When click 2 times to Profile Picture select app getting crash "Fix this problem"
                    if(Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()&& selectedImageURI!=null){
                        signUser()
                        Toast.makeText(context, "Sign is Success", Toast.LENGTH_LONG).show()
                        val action = SignUpFragmentDirections.actionSignUpFragmentToMainFragment()
                        findNavController().navigate(action)
                    }
                    if(selectedImageURI==null){
                        Toast.makeText(activity, "Please load profile picture", Toast.LENGTH_SHORT).show()
                    }
                    if (password.length<6){
                        Toast.makeText(activity, "Please enter minimum 6 characters for password", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(activity, "Invalid email adress", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context, "Please fill all information", Toast.LENGTH_SHORT).show()
                }
            }).show()
        }
        binding.selectPhotoFAB.setOnClickListener {
          selectImage()
            println("xxx")
        }

    }
    fun signUser() {
        val email = userSign.text.toString().trim()
        val password = passwordSign.text.toString().trim()

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
    private fun userInfoTake() {
        val uuid = UUID.randomUUID()
        val selectableImage = "${uuid}.jpg"
        val storageRef = Util.storage.reference
        val imageRef=storageRef.child("userProfileImages").child(selectableImage)
        if (selectedImage != null) {
            imageRef.putFile(selectedImage!!).addOnSuccessListener {taskSnapshot->
                Toast.makeText(activity, "SUCCESS", Toast.LENGTH_SHORT).show()
                val loadedImageReference = FirebaseStorage.getInstance()
                    .reference.child("userProfileImages").child(selectableImage)
                loadedImageReference.downloadUrl.addOnSuccessListener{uri->
                    val userUUID = Util.auth.currentUser!!.uid
                    val userEmail = Util.auth.currentUser!!.email.toString()
                    val userName = binding.userNameET.text.toString()
                    val petName = binding.petName.text.toString()
                    val downloadImage = uri.toString()
                    val password=binding.passwordSign.text.toString()

                    val userInfoMap = HashMap<String, Any>()
                    userInfoMap.put("userImage",downloadImage)
                    userInfoMap.put("userUUID", userUUID)
                    userInfoMap.put("userEmail", userEmail)
                    userInfoMap.put("userName", userName)
                    userInfoMap.put("petName", petName)
                    userInfoMap.put("password",password)
                    database.collection("userProfileInfo").add(userInfoMap).addOnSuccessListener {
                        Toast.makeText(requireContext(), "DONE", Toast.LENGTH_SHORT).show()
                    }
                        .addOnFailureListener {
                    }

                }

    }
        }
    }
    private fun selectImage() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1)
        } else {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 2)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val gallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(gallery, 2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            selectedImage = data.data
            if (selectedImage != null) {
                val source =ImageDecoder.createSource(requireActivity().contentResolver, selectedImage!!)
                selectedImageURI = ImageDecoder.decodeBitmap(source)
                signImageView.setImageBitmap(selectedImageURI)

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}