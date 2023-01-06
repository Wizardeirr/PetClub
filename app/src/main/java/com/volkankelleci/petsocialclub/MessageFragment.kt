package com.volkankelleci.petsocialclub

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
import com.google.firebase.storage.FirebaseStorage
import com.volkankelleci.petsocialclub.databinding.FragmentMessageBinding
import com.volkankelleci.petsocialclub.util.Util.database
import com.volkankelleci.petsocialclub.util.Util.storage
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_message.*
import java.util.UUID

class MessageFragment : Fragment() {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!
    var selectedImage: Uri? = null
    var selectedImageURI: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.selectImage.setOnClickListener {
            selectImage()
        }
        binding.shareButton.setOnClickListener {
            storeImage(view)
        }
    }
    private fun storeImage(view: View) {
        val uuid = UUID.randomUUID()
        val selectableImage = "${uuid}.jpg"
        val storageRef = storage.reference
        val imageRef=storageRef.child("images").child(selectableImage)
                if (selectedImage != null) {
                    imageRef.putFile(selectedImage!!).addOnSuccessListener {taskSnapshot->
                        Toast.makeText(activity, "SUCCESS", Toast.LENGTH_SHORT).show()
                        val loadedImageReference =FirebaseStorage.getInstance()
                            .reference.child("images").child(selectableImage)
                        loadedImageReference.downloadUrl.addOnSuccessListener {uri->
                                val downloadImage = uri.toString()
                                val userComment=commentText.text.toString()
                                val date=Timestamp.now()
                                val userTitle=titleText.text.toString()
                            val postHashMap= hashMapOf<String,Any>()
                            postHashMap.put("imageurl",downloadImage)
                            postHashMap.put("usercomment",userComment)
                            postHashMap.put("date",date)
                            postHashMap.put("usertitle",userTitle)
                    database.collection("Post").add(postHashMap).addOnCompleteListener { task->
                        if(task.isSuccessful){
                            Toast.makeText(activity, "Image Downloaded", Toast.LENGTH_SHORT).show()
                            val action =MessageFragmentDirections.actionMessageFragmentToUsersHomeFragment()
                            findNavController().navigate(action)
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
                selectImage.setImageBitmap(selectedImageURI)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}