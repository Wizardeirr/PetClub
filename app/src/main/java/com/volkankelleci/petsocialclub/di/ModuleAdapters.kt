package com.volkankelleci.petsocialclub.di

import com.volkankelleci.petsocialclub.data.Post
import com.volkankelleci.petsocialclub.data.PrivateMessage
import com.volkankelleci.petsocialclub.data.UserInfo
import com.volkankelleci.petsocialclub.domain.helpers.lastprivatemessagelist.LastPrivateMessageListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleAdapters {


    @Singleton
    @Provides
    fun providePrivateMessageList(): ArrayList<PrivateMessage> {
        return ArrayList()
    }
    @Singleton
    @Provides
    fun providePostList(): ArrayList<Post> {
        return ArrayList()
    }
    @Singleton
    @Provides
    fun userInfos(): ArrayList<UserInfo> {
        return ArrayList()
    }

    @Singleton
    @Provides
    fun provideLastPrivateMessageListAdapterListener() =
        object : LastPrivateMessageListAdapter.Listener {
            override fun onItemClickListener(privateMessage: PrivateMessage) {
                return onItemClickListener(privateMessage)

            }
        }

}