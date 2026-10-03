package com.example.ojora.ui.screens.home

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
data class FeedDetailRoute(val index: Int)

@Serializable
object ActivityScreenRoute

fun NavController.navigateToFeedDetail(index: Int) {
    navigate(FeedDetailRoute(index))
}

fun NavController.navigateToActivitySection() {
    navigate(ActivityScreenRoute)
}

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    onCtaClick: (String, String) -> Unit
) {
    composable<FeedDetailRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<FeedDetailRoute>()
        FeedDetailScreen(
            index = route.index,
            onBackClick = { navController.popBackStack() },
            onCtaClick = onCtaClick
        )
    }

    composable<ActivityScreenRoute> {
        ActivityScreen(
            onBackClick = { navController.popBackStack() }
        )
    }
}

@Composable
fun HomeScreenContent(
    onFeedPostClick: (Int) -> Unit,
    onActivityClick: () -> Unit,
    onCtaClick: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Home Tab Workspace", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { onFeedPostClick(1) }) {
            Text("Open Feed Post #1")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onActivityClick) {
            Text("Open Activity Section")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { onCtaClick("Announcement", "https://example.com") }) {
            Text("Open Web Link CTA")
        }
    }
}

@Composable
fun FeedDetailScreen(
    index: Int,
    onBackClick: () -> Unit,
    onCtaClick: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Feed Post Detail #$index", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onCtaClick("Feed Link", "https://example.com/feed/$index") }) {
            Text("View Full Article (Web)")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onBackClick) {
            Text("Back")
        }
    }
}

@Composable
fun ActivityScreen(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("User Activity & History", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick) {
            Text("Back to Dashboard")
        }
    }
}
