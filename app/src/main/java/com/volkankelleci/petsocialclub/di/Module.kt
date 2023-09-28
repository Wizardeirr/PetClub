package com.volkankelleci.petsocialclub.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.volkankelleci.petsocialclub.room.UserUUIDDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun injectRoomDB(@ApplicationContext context:Context)= Room.databaseBuilder(context,
    UserUUIDDataBase::class.java,"useruuiddb").build()

    @Singleton
    @Provides
    fun injectDao(database: UserUUIDDataBase)=database.userDao()



}