package com.volkankelleci.petsocialclub.repo

import com.volkankelleci.petsocialclub.room.UserUUIDData
import kotlinx.coroutines.flow.Flow

interface FakeUUIDRepository {
    suspend fun getAllUsers(): Flow<List<UserUUIDData>>
    suspend fun insertUser(user: UserUUIDData)
    suspend fun deleteUser(user: UserUUIDData)
}