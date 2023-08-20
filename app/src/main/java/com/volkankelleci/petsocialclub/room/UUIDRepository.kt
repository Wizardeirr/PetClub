package com.volkankelleci.petsocialclub.room

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UUIDRepository(private val userUUIDDao: UserUUIDDao) : FakeUUIDRepository {

    override suspend fun getAllUsers(): List<UserUUIDData> = withContext(Dispatchers.IO) {
        return@withContext userUUIDDao.getAll()
    }

    override suspend fun insertUser(user: UserUUIDData) = withContext(Dispatchers.IO) {
        userUUIDDao.insertAll(user)
    }

    override suspend fun deleteUser(user: UserUUIDData) = withContext(Dispatchers.IO) {
        userUUIDDao.deleteAll(user)
    }
}