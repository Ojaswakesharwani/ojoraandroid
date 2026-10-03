package com.example.ojora.ui.screens.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Serializable
object WelcomeRoute

fun NavGraphBuilder.loginGraph(
    navController: NavController,
    onLoginSuccess: () -> Unit

){
    composable<LoginRoute>{
        LoginScreen(
            onLoginSuccess = onLoginSuccess,
            onNavigateToWelcome = {navController.navigate(WelcomeRoute)}

        )
    }

}


@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToWelcome: () -> Unit
) {
    var username by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username / Mobile") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onLoginSuccess,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text("Sign In")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onNavigateToWelcome,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text("Go to Welcome Info")
        }
    }
}

@Composable
fun WelcomeScreen(onContinue: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome to Ojora SuperApp!", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onContinue) {
            Text("Proceed to Login")
        }
    }
}
