package com.example.ojora

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.ojora.task.RedirectionResolver
import com.example.ojora.ui.navigation.OjoraNavHost
import com.example.ojora.ui.screens.dashboard.DashboardRoute
import com.example.ojora.ui.screens.login.LoginRoute
import com.example.ojora.ui.theme.OjoraTheme
import com.example.ojora.utility.AppEventBus
import com.example.ojora.utility.EventType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Main launcher Activity demonstrating application entry point, session state,
 * deep link redirection resolution, event bus handlers, and root Jetpack Compose Navigation.
 */
const val STEP_OF_EXECUTION = "Step"
class MainActivity : ComponentActivity() {

    private val redirectionResolver = RedirectionResolver()
    private var isUserLoggedIn by mutableStateOf<Boolean?>(null)




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(STEP_OF_EXECUTION,"Step 1 Execution ")

        lifecycleScope.launch {
            Log.d(STEP_OF_EXECUTION,"Step 2 Execution bg ")
            delay(200000)
            redirectionResolver.resolveAndSetRedirection(intent)
            Log.d(STEP_OF_EXECUTION,"Step 3 Execution bg ")
            // Simulated initial session check
            delay(200000)
            isUserLoggedIn = false
        }

        enableEdgeToEdge()
        Log.d(STEP_OF_EXECUTION,"Step 4 Execution  ")


        setContent {
            OjoraTheme {
                Log.d(STEP_OF_EXECUTION,"Step 5 Execution  ")

                if (isUserLoggedIn != null) {
                    Log.d(STEP_OF_EXECUTION,"Step 6 Execution ${isUserLoggedIn} ")

                    val navController = rememberNavController()
                    var isSignInRequired by remember { mutableStateOf(false) }
                    Log.d(STEP_OF_EXECUTION,"Step 7 Execution ${isSignInRequired} ")


                    LaunchedEffect(Unit) {
                        Log.d(STEP_OF_EXECUTION,"Step 8 Execution ${isSignInRequired} ")

                        AppEventBus.events.collect { eventType ->
                            if (eventType == EventType.SIGN_IN_REQUIRED) {
                                isSignInRequired = true

                            }
                        }
                    }
                    Log.d(STEP_OF_EXECUTION,"Step 9 Execution ${isUserLoggedIn} ")

                    LaunchedEffect(navController, isUserLoggedIn, redirectionResolver.pendingRoute) {
                        if (isUserLoggedIn == true && redirectionResolver.pendingRoute != null) {
                            redirectionResolver.consumePendingRoute()?.let { route ->
                                navController.popBackStack(DashboardRoute, inclusive = false)
                                navController.navigate(route)
                            }
                        }
                    }

                    ObserveForceLogout(isUserLoggedIn = isUserLoggedIn) {
                        isUserLoggedIn = false
                        navController.navigate(LoginRoute) {
                            popUpTo(0) { inclusive = true }
                        }
                    }

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        contentWindowInsets = WindowInsets(0, 0, 0, 0)
                    ) { innerPadding ->
                        OjoraNavHost(
                            navController = navController,
                            startDestination = if (isUserLoggedIn == true) DashboardRoute else LoginRoute,
                            onLoginSuccess = {
                                isUserLoggedIn =true
                                navController.navigate(DashboardRoute){
                                    popUpTo(0) { inclusive=true }
                                }

                            }
                              ,
                            onLogoutClick = {
                                isUserLoggedIn = false
                                navController.navigate(LoginRoute) {
                                    popUpTo(0) { inclusive = true }
                                }
                            },
                            modifier = Modifier.padding(innerPadding)
                        )

                        if (isSignInRequired) {
                            SignInRequiredDialog(
                                onConfirm = {
                                    isUserLoggedIn = false
                                    isSignInRequired = false
                                    navController.navigate(LoginRoute) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                onDismiss = { isSignInRequired = false }
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ObserveForceLogout(
        isUserLoggedIn: Boolean?,
        onLogoutSuccess: () -> Unit
    ) {
        LaunchedEffect(isUserLoggedIn) {
            AppEventBus.events.collect { eventType ->
                if (eventType == EventType.FORCE_LOGOUT) {
                    if (isUserLoggedIn == true) {
                        onLogoutSuccess()
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        lifecycleScope.launch {
            redirectionResolver.resolveAndSetRedirection(intent)
        }
    }
}

@Composable
fun SignInRequiredDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Sign-in Required") },
        text = { Text("It's been a while. Please sign in again to continue.") },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}