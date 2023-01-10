package com.volkankelleci.petsocialclub.util

import com.google.firebase.firestore.FieldValue

class ChatData(
    var chatText:String,
    var userAuth:String,
    var date:FieldValue,
) {
}