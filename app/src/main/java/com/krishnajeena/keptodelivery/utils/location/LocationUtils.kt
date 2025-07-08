package com.krishnajeena.keptodelivery.utils.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.currentRecomposeScope
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority

object LocationUtils{
    fun getCurrentLocation(
        context: Context,
        useHighAccuracy: Boolean = true,
        onLocationFetched: (Location) -> Unit
    ) {
        Log.i("TAG", "getCurrentLocation: called")
        val fusedClient = LocationServices.getFusedLocationProviderClient(context)

        val permission =  if (useHighAccuracy)
            Manifest.permission.ACCESS_FINE_LOCATION
        else
            Manifest.permission.ACCESS_COARSE_LOCATION

        Log.i("TAG", "useHighAccuracy: $useHighAccuracy")
        Log.i("TAG", "getCurrentLocation: $permission")

        if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            Log.i("TAG", "getCurrentLocation: Permission not granted")
            return
        }

        fusedClient.lastLocation.addOnSuccessListener { location ->
            Log.i("TAG", "getCurrentLocation: ${location}")
            if(location != null){
                Log.i("TAG", "getCurrentLocation: ${location.latitude}")
                onLocationFetched(location)
            }
            else{
                val request = LocationRequest.Builder(
                    if(useHighAccuracy) Priority.PRIORITY_HIGH_ACCURACY else Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    1000
                ).setMaxUpdates(1).build()

                val callback = object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        fusedClient.removeLocationUpdates(this)
                        result.lastLocation?.let { onLocationFetched(it) }
                    }
                }

                fusedClient.requestLocationUpdates(request, callback, Looper.getMainLooper())


            }
        }
            .addOnFailureListener{
                Log.i("TAG", "getCurrentLocation: ${it.message}")
            }
    }

}

fun ensureLocationEnabled(
    context: Context,
    activity: Activity,
    onGpsEnabled: () -> Unit,
    onGpsDenied: () -> Unit
) {
    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

    val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)
        .setAlwaysShow(true) // Important: show dialog even if already off

    val client = LocationServices.getSettingsClient(context)
    val task = client.checkLocationSettings(builder.build())

    task.addOnSuccessListener {
        // GPS is ON
        onGpsEnabled()
    }

    task.addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
            try {
                exception.startResolutionForResult(activity, 1001) // Request code to handle result
            } catch (sendEx: IntentSender.SendIntentException) {
                onGpsDenied()
            }
        } else {
            onGpsDenied()
        }
    }
}
