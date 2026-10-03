package com.example.ojora.ui.screens.book

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
data class BookDetailRoute(val serviceId: String)

fun NavController.navigateToBookDetail(serviceId: String) {
    navigate(BookDetailRoute(serviceId))
}

fun NavGraphBuilder.bookGraph(navController: NavController) {
    composable<BookDetailRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<BookDetailRoute>()
        BookDetailScreen(
            serviceId = route.serviceId,
            onBackClick = { navController.popBackStack() }
        )
    }
}

@Composable
fun BookScreenContent(onServiceClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Booking & Facilities Workspace", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { onServiceClick("BUSINESS_LOUNGE") }) {
            Text("Book Business Lounge")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { onServiceClick("MAINTENANCE") }) {
            Text("Request Maintenance")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { onServiceClick("VISITOR_PASS") }) {
            Text("Create Visitor Pass")
        }
    }
}

@Composable
fun BookDetailScreen(
    serviceId: String,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Service Booking: $serviceId", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick) {
            Text("Back")
        }
    }
}
