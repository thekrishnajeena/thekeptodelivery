package com.krishnajeena.keptodelivery.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krishnajeena.keptodelivery.domain.use_case.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : ViewModel(){
    fun logout(){
        viewModelScope.launch{
            logoutUseCase()
        }
    }
}