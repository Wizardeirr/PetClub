package com.volkankelleci.petsocialclub.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserUUIDData(
    @PrimaryKey(autoGenerate = true)
    var message:String,
    var fromUUID:String,
    var toUUID: String,
    var timestamp: String,
    var chatUser:String
)
