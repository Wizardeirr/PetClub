package com.volkankelleci.petsocialclub.data

class PrivateMessage(
    var message:String,
    var fromUUID:String,
    var toUUID: ArrayList<String>,
    var timestamp: String,
    var chatUser:String)