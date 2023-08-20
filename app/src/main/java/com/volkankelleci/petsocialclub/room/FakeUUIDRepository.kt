package com.volkankelleci.petsocialclub.room

interface FakeUUIDRepository {
    suspend fun getAllUsers(): List<UserUUIDData>
    suspend fun insertUser(user: UserUUIDData)
    suspend fun deleteUser(user: UserUUIDData)
}