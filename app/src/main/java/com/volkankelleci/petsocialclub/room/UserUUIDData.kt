package com.volkankelleci.petsocialclub.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserUUIDData(
    @PrimaryKey(autoGenerate = true)
    val uuid:String?,
)
