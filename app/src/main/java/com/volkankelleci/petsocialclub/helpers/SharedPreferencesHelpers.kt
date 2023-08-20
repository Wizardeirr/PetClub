package com.volkankelleci.petsocialclub.helpers

import android.content.Context

object SharedPreferencesHelpers {
    //toUUID
    fun getToUUIDFromSharedPreferences(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("toUUID", "") ?: ""
    }
}