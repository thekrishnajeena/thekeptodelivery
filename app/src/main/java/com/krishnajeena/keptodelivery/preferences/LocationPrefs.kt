package com.krishnajeena.keptodelivery.preferences

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore

object LocationPrefs {
    val Context.datastore by preferencesDataStore(name = "location_prefs")
    val KEY_LATITUDE = doublePreferencesKey("latitude")
    val KEY_LONGITUDE = doublePreferencesKey("longitude")

    suspend fun saveLocation(context: Context, latitude: Double, longitude: Double) {
        context.datastore.edit { prefs ->
            prefs[KEY_LATITUDE] = latitude
            prefs[KEY_LONGITUDE] = longitude
        }
    }
}