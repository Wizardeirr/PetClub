package com.volkankelleci.petsocialclub.viewmodel

import androidx.lifecycle.ViewModel
import com.volkankelleci.petsocialclub.repo.UUIDRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository:UUIDRepository
):ViewModel() {



}