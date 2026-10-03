package com.example.ojora.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDetailRoute(val section: String)

fun NavController.navigateToProfileDetail(section: String) {
    navigate(ProfileDetailRoute(section))
}

fun NavGraphBuilder.profileGraph(navController: NavController) {
    composable<ProfileDetailRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<ProfileDetailRoute>()
        ProfileDetailScreen(
            section = route.section,
            onBackClick = { navController.popBackStack() }
        )
    }
}

@Composable
fun ProfileScreenContent(
    onProfileDetailClick: (String) -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("User Profile Workspace", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { onProfileDetailClick("PERSONAL_DETAILS") }) {
            Text("Edit Personal Details")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { onProfileDetailClick("SETTINGS") }) {
            Text("App Settings")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onLogoutClick) {
            Text("Sign Out")
        }
    }
}

@Composable
fun ProfileDetailScreen(
    section: String,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Profile Section: $section", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick) {
            Text("Back")
        }
    }
}
