package com.volkankelleci.petsocialclub

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PetSocialClubApplication: Application() {
    companion object {
        private lateinit var instance: PetSocialClubApplication

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}