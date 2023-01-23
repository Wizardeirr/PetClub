package com.volkankelleci.petsocialclub.util

import com.google.firebase.Timestamp
import java.util.UUID

class PrivateMessage(
    var message:String,
    var uuid:UUID,
    var timestamp: Timestamp,
    var chatUser:String) {
}