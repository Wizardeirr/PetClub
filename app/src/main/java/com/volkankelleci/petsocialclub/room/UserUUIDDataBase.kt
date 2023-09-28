package com.volkankelleci.petsocialclub.room

import android.content.Context
import android.os.Build.VERSION
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserUUIDData::class], version = 1)
abstract class UserUUIDDataBase(context:Context):RoomDatabase() {
    abstract fun userDao():UserUUIDDao

}