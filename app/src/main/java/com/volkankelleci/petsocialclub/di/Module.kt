package com.volkankelleci.petsocialclub.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.volkankelleci.petsocialclub.repo.UUIDRepository
import com.volkankelleci.petsocialclub.room.UserUUIDDataBase
import com.volkankelleci.petsocialclub.viewmodel.UsersHomeFragmentVM
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.UUID
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
    @Singleton
    @Provides
    fun firebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()

    }
    @Provides
    @Singleton
    fun provideUsersHomeFragmentViewModel(repository:UUIDRepository) : UsersHomeFragmentVM {
        return UsersHomeFragmentVM(repository)
    }



}