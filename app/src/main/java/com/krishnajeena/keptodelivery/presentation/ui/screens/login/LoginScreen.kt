package com.krishnajeena.keptodelivery.presentation.ui.screens.login

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.krishnajeena.keptodelivery.presentation.viewmodel.LoginViewModel

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreen(modifier: Modifier = Modifier,
                navController : NavHostController,
                viewModel: LoginViewModel = hiltViewModel()
) {


    Scaffold { innerPadding ->

        val context = LocalContext.current
    var phone by remember { mutableStateOf("") }
    Column(modifier = modifier
        .fillMaxSize()
        .padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally,
        ){
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        Text("kepto", fontFamily = FontFamily.Serif, fontSize = 80.sp,
            modifier = Modifier.padding(start = 10.dp))

        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Text(
                "Groceries\ndelivered in\n10 minutes",
                fontFamily = FontFamily.Serif,
                fontSize = 30.sp,
                modifier = Modifier.padding(start = 10.dp, bottom = 20.dp)
            )
        }
        OutlinedTextField(value = phone, onValueChange = {phone = it}, placeholder={Text("Enter Phone Number")},
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(IntrinsicSize.Max)
            , singleLine = true,
            shape = RoundedCornerShape(25.dp),
            leadingIcon = {
                Text(
                    text = "+91",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    modifier = Modifier.padding(start=16.dp, end = 16.dp)
                )
            })

        OutlinedButton(onClick = {
            val formattedPhone = if (phone.startsWith("+")) phone else "+91$phone"

            viewModel.sendOtp(
    formattedPhone,
    activity = context as Activity,
    onVerficatinoIdReceived = {
        verificationId ->
        navController.navigate("otp_screen/$verificationId/$phone")
    },
    onError = { error ->
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        Log.i("LoginScreen", error)
    }

)
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(IntrinsicSize.Max),
            ) {
            Text("Submit")
        }

Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom,
    horizontalAlignment = Alignment.CenterHorizontally){
        Text("By continuing you agree to our", fontSize = 12.sp)
Row(modifier = Modifier){
        Text("Terms of Use")
        Text(" & ")
        Text("Privacy Policy")
}
}
    }
    }
}

@Composable
fun OtpScreen(modifier: Modifier = Modifier,
              navController: NavHostController,
              verificationId: String,
              phoneNumber: String,
              viewModel: LoginViewModel = hiltViewModel()) {
    Scaffold { innerPadding ->

        var otp by remember { mutableStateOf("") }
        val context = LocalContext.current

        Column(modifier = modifier
            .fillMaxSize()
            .padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally,
        ){

            Text("Enter OTP sent to $phoneNumber", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = otp,
                onValueChange = { otp = it },
                label = { Text("OTP") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.verifyOtp(
                    verificationId,
                    otp,
                    onSuccess = { navController.navigate("home") },
                    onFailure = { Toast.makeText(context, it, Toast.LENGTH_LONG).show() },
                    context = context,
                    phoneNumber = phoneNumber
                )
            }) {
                Text("Verify")
            }
        }
    
}}