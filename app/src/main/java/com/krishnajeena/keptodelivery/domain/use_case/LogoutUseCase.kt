package com.krishnajeena.keptodelivery.domain.use_case

import com.krishnajeena.keptodelivery.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
){
    suspend operator fun invoke(){
        repository.logout()
    }
}