package com.volkankelleci.petsocialclub.repo

import com.volkankelleci.petsocialclub.room.UserUUIDDao
import com.volkankelleci.petsocialclub.room.UserUUIDData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UUIDRepository @Inject constructor(
    private val userUUIDDao: UserUUIDDao
) : FakeUUIDRepository {

    override suspend fun getAllUsers(): Flow<List<UserUUIDData>>{
        return userUUIDDao.getAll()
    }

    override suspend fun insertUser(user: UserUUIDData){
        userUUIDDao.insertAll(user)
    }

    override suspend fun deleteUser(user: UserUUIDData){
        userUUIDDao.deleteAll(user)
    }
}