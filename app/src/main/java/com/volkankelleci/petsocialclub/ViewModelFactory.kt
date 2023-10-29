package com.volkankelleci.petsocialclub

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.volkankelleci.petsocialclub.viewmodel.UsersHomeFragmentVM
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    myViewModelProvider: Provider<UsersHomeFragmentVM>
) : ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        UsersHomeFragmentVM::class.java to myViewModelProvider
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }
}