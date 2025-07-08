package com.krishnajeena.keptodelivery.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


object LoginPrefs{
    val Context.datastore by preferencesDataStore(name = "login_prefs")

    val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    val KEY_PHONE = stringPreferencesKey("phone_number")

    suspend fun setLoggedIn(context: Context, phoneNumber: String){
        context.datastore.edit{ prefs ->
            prefs[KEY_IS_LOGGED_IN] = true
            prefs[KEY_PHONE] = phoneNumber
        }
    }

    fun isLoggedIn(context: Context): Flow<Boolean> = context.datastore.data
        .map{ prefs -> prefs[KEY_IS_LOGGED_IN] ?: false}

    fun getPhoneNumber(context: Context): Flow<String?> = context.datastore.data
        .map{prefs -> prefs[KEY_PHONE]}
}