package com.portal.weatherapp.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel(){

    private val _isRegistrationCompleted = MutableStateFlow<Boolean>(false)
    val isRegistrationCompleted = _isRegistrationCompleted.asStateFlow()

    fun changeRegistrationState(status: Boolean){
        _isRegistrationCompleted.value = status
    }


}