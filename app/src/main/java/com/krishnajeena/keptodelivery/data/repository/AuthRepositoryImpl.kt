package com.krishnajeena.keptodelivery.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.google.firebase.auth.FirebaseAuth
import com.krishnajeena.keptodelivery.domain.repository.AuthRepository
import com.krishnajeena.keptodelivery.preferences.LoginPrefs
import com.krishnajeena.keptodelivery.preferences.LoginPrefs.datastore
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val context: Context
) : AuthRepository {

    override suspend fun logout(){
        context.datastore.edit{
            it[LoginPrefs.KEY_IS_LOGGED_IN] = false
            it[LoginPrefs.KEY_PHONE] = ""
        }

        FirebaseAuth.getInstance().signOut()
    }

}