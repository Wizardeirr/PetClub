package com.volkankelleci.petsocialclub.profileviewfills

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.volkankelleci.petsocialclub.MessageFragmentDirections
import com.volkankelleci.petsocialclub.R
import com.volkankelleci.petsocialclub.data.UsersData
import com.volkankelleci.petsocialclub.databinding.FragmentMessageBinding
import com.volkankelleci.petsocialclub.databinding.FragmentProfileFillBinding
import com.volkankelleci.petsocialclub.util.Util
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.fragment_profile_fill.*
import kotlinx.android.synthetic.main.fragment_profile_fill.view.*
import kotlinx.android.synthetic.main.fragment_user_chat.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class ProfileFillFragment : Fragment() {
    private var _binding: FragmentProfileFillBinding? = null
    private val binding get() = _binding!!
    var selectedImage: Uri? = null
    var selectedImageURI: Bitmap? = null
    private val userProfile= Firebase.firestore.collection("UserProfileInfos")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileFillBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.petImage.setOnClickListener {
            selectImage()
        }
        saveButton.setOnClickListener {
            val petName=petName.text.toString()
            val petage=petAge.text.toString()
            val petSpecies=petSpecies.text.toString()
            val petWeight=petKg.text.toString()
            val petGender=petGender.text.toString()
            val vaccineInfo=petVaccine.text.toString()
            val ownersName=petOwnerName.text.toString()
            val petImages=petImage.toString()
            val dataInput=UsersData(petName,petage,petSpecies,petWeight,petGender,vaccineInfo,ownersName,petImages)


            saveUser(dataInput)
            storageImage()

            if (petName.isNotEmpty()&&petage.isNotEmpty()&&petSpecies.isNotEmpty()&&petWeight.isNotEmpty()&&petGender.isNotEmpty()
                &&vaccineInfo.isNotEmpty()&&ownersName.isNotEmpty()){
                Toast.makeText(activity, "Profile Created", Toast.LENGTH_LONG).show()
                val action=ProfileFillFragmentDirections.actionProfileFillFragmentToUsersHomeFragment()
                findNavController().navigate(action)
            }
        }
    }
    fun saveUser(user: UsersData)= CoroutineScope(Dispatchers.IO).launch {
        try {
            userProfile.add(user).await()
            withContext(Dispatchers.Main){
            }

        }catch (e:Exception){
            withContext(Dispatchers.Main){
                e.printStackTrace()
            }
        }
    }
    private fun storageImage(){
        val uuid = UUID.randomUUID()
        val selectableImage = "${uuid}.jpg"
        val storageRef = Util.storage.reference
        val imageRef=storageRef.child("petImage").child(selectableImage)
        if (selectedImage != null) {
            imageRef.putFile(selectedImage!!).addOnSuccessListener {taskSnapshot->
                Toast.makeText(activity, "SUCCESS", Toast.LENGTH_SHORT).show()
                val loadedImageReference = FirebaseStorage.getInstance()
                    .reference.child("petImage").child(selectableImage)
                loadedImageReference.downloadUrl.addOnSuccessListener {uri->
                    val downloadImage = uri.toString()
                    val postHashMap= hashMapOf<String,Any>()
                    postHashMap.put("petUrl",downloadImage)
                    Util.database.collection("UserProfileInfos").add(postHashMap).addOnCompleteListener { task->
                        if(task.isSuccessful){
                            Toast.makeText(activity, "Image Downloaded", Toast.LENGTH_SHORT).show()
                            println(downloadImage)

                        }
                    }.addOnFailureListener {
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
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
                val source =
                    ImageDecoder.createSource(requireActivity().contentResolver, selectedImage!!)
                selectedImageURI = ImageDecoder.decodeBitmap(source)
                petImage.setImageBitmap(selectedImageURI)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}