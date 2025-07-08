package com.krishnajeena.keptodelivery.presentation.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.krishnajeena.keptodelivery.preferences.LoginPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel(){

    private val auth = FirebaseAuth.getInstance()

    fun sendOtp(
        phone: String,
        activity: Activity,
        onVerficatinoIdReceived: (String) -> Unit,
        onError: (String) -> Unit){

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(credential: PhoneAuthCredential){
Log.i("LoginViewModel", "onVerificationCompleted: $credential")
                }

                override fun onVerificationFailed(e: FirebaseException){
                    onError(e.localizedMessage ?: "Verification Failed")
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken){
                    onVerficatinoIdReceived(verificationId)
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOtp(
        verificationId: String,
        otp: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        context: Context,
        phoneNumber: String

        ){
        val credential = PhoneAuthProvider.getCredential(verificationId, otp)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                task ->
                if(task.isSuccessful){
                    viewModelScope.launch{
                     LoginPrefs.setLoggedIn(context, phoneNumber)
                    onSuccess.invoke()

                    }
                } else{
                    onFailure(task.exception?.localizedMessage ?: "Verification Failed")
                }
            }
            .addOnFailureListener {
                onFailure(it.localizedMessage ?: "Verification Failed")
            }
    }
}