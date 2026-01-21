package com.example.bookstorev2.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.bookstorev2.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    fun setUser(user: User){
        _user.value = user
    }

    fun clearUser(){
        _user.value = null
    }

}