package com.krishnajeena.keptodelivery.presentation.viewmodel

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krishnajeena.keptodelivery.preferences.LocationPrefs
import com.krishnajeena.keptodelivery.utils.location.LocationUtils.getCurrentLocation
import com.krishnajeena.keptodelivery.utils.location.ensureLocationEnabled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel(){

    fun ensureLocationAndFetch(context: Context, activity:
    Activity) {
        ensureLocationEnabled(
            context,
            activity,
            onGpsEnabled = {
                getCurrentLocation(context) { location ->
                    viewModelScope.launch {
                        LocationPrefs.saveLocation(context, location.latitude, location.longitude)
                       // authRepository.uploadLocationToFirebase("userId", location.latitude, location.longitude)
                        Toast.makeText(context, "Location saved ${location}", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            onGpsDenied = {
                Toast.makeText(context, "Please enable location", Toast.LENGTH_SHORT).show()
            }
        )
    }

}