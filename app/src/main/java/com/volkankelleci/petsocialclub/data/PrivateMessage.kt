package com.volkankelleci.petsocialclub.data

import java.util.Date

class PrivateMessage(
    var message:String,
    var fromUUID:String,
    var toUUID:String,
    var timestamp: String,
    var chatUser:String)