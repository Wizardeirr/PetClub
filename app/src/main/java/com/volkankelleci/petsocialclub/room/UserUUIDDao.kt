package com.volkankelleci.petsocialclub.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserUUIDDao {
    @Query("SELECT * FROM useruuiddata")
    fun getAll():List<UserUUIDData>
    @Insert
    fun insertAll(vararg users:UserUUIDData)
    @Delete
    fun deleteAll(user:UserUUIDData)
}