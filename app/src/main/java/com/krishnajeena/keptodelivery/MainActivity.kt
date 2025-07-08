package com.krishnajeena.keptodelivery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.krishnajeena.keptodelivery.preferences.LoginPrefs
import com.krishnajeena.keptodelivery.presentation.ui.screens.login.HomeScreen
import com.krishnajeena.keptodelivery.presentation.ui.screens.login.LoginScreen
import com.krishnajeena.keptodelivery.presentation.ui.screens.login.OtpScreen
import com.krishnajeena.keptodelivery.ui.theme.KeptoDeliveryTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var locationResolutionLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        locationResolutionLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.i("TAG", "onCreate: Location Enabled")
            } else {
                Log.i("TAG", "onCreate: Location Denied")
            }


            setContent {
                KeptoDeliveryTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                        val navController = rememberNavController()

                        val context = LocalContext.current
                        val isLoggedIn by LoginPrefs.isLoggedIn(context)
                            .collectAsState(initial = false)
                        NavHost(
                            navController = navController,
                            startDestination = if (isLoggedIn) "home" else "login",
                        ) {

                            composable("login") {
                                LoginScreen(navController = navController)
                            }

                            composable(
                                "otp_screen/{verificationId}/{phoneNumber}",
                                arguments = listOf(
                                    navArgument("verificationId") { type = NavType.StringType },
                                    navArgument("phoneNumber") { type = NavType.StringType }
                                )) { backStackEntry ->
                                val verificationId =
                                    backStackEntry.arguments?.getString("verificationId")
                                val phoneNumber = backStackEntry.arguments?.getString("phoneNumber")
                                OtpScreen(
                                    navController = navController,
                                    verificationId = verificationId!!,
                                    phoneNumber = phoneNumber!!
                                )

                            }

                            composable("home") {
                                HomeScreen()
                            }
                        }

                    }
                }
            }
        }
    }
}