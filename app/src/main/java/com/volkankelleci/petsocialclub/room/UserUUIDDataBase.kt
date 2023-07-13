package com.volkankelleci.petsocialclub.room

import android.os.Build.VERSION
import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase

@Database(entities = [UserUUIDData::class], version = 1)
abstract class UserUUIDDataBase:RoomDatabase() {
    abstract fun userDao():UserUUIDDao
}