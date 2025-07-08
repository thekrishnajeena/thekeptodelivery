package com.krishnajeena.keptodelivery.presentation.ui.screens.login

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.krishnajeena.keptodelivery.presentation.viewmodel.HomeViewModel
import com.krishnajeena.keptodelivery.utils.location.LocationUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()) {
    Scaffold { innerPadding ->

        Column (modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally){

            val context = LocalContext.current
            var locationGot by remember{ mutableStateOf(false)}
            val sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
                confirmValueChange = { newState ->
                    if(newState == SheetValue.Hidden && !locationGot){
                        false
                    }
                    else true
                }
            )

            val locationPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { permissions ->

                    val isCoarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                    val isFineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true

                    if(isFineGranted){
                        LocationUtils.getCurrentLocation(context, onLocationFetched = { it ->
                            locationGot = true
                        Toast.makeText(context, "Location fetched ${it}", Toast.LENGTH_SHORT).show()
                            Log.i("TAG", "HomeScreen: ${it}")
                        },
                            useHighAccuracy = true)
                    }
                    else{
                        LocationUtils.getCurrentLocation(context, onLocationFetched = { it ->
                            locationGot = true
                            Toast.makeText(context, "Location fetched ${it}", Toast.LENGTH_SHORT).show()
                            Log.i("TAG", "HomeScreen: ${it}")
                        },
                            useHighAccuracy = false)
                        Log.i("TAG", "HomeScreen: not Granted")
                    }
                }
            )

         ModalBottomSheet(onDismissRequest = {

         }, sheetState = sheetState){

             Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally){
             Text("Local permission is off")
             Text("Please enable local permission for better delivery experience",
                 fontSize = 10.sp, textAlign = TextAlign.Center)

                 Button(onClick = {

                     val fineGranted = ContextCompat.checkSelfPermission(context,
                         Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

                     val coarseGranted = ContextCompat.checkSelfPermission(
                         context, Manifest.permission.ACCESS_COARSE_LOCATION
                     ) == PackageManager.PERMISSION_GRANTED

                     if(fineGranted || coarseGranted){
                         viewModel.ensureLocationAndFetch(context, activity = context as Activity)
                     } else{
                         locationPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
                         Log.i("TAG", "HomeScreen: Button Clicked")
                     }


                 }){
                     Text("Continue")
                 }

                 Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                     Text("Select your address")

                     Text("See All")
                 }

                 OutlinedButton(onClick = {}) {
                     Text("Search your Location")
                 }
         }
         }


        }
    }
}