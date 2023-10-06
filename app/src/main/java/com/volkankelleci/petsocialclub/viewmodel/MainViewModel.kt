package com.volkankelleci.petsocialclub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volkankelleci.petsocialclub.repo.UUIDRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository:UUIDRepository
):ViewModel() {

    private fun takesUserUUID()= CoroutineScope(Dispatchers.IO).launch{
        repository.getAllUsers()
    }
}