package com.volkankelleci.petsocialclub.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

object Util {
     var auth: FirebaseAuth= FirebaseAuth.getInstance()
     var storage:FirebaseStorage=FirebaseStorage.getInstance()
     var database:FirebaseFirestore=FirebaseFirestore.getInstance()

}