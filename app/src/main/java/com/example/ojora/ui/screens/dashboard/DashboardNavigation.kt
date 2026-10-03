package com.example.ojora.ui.screens.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.ojora.ui.screens.book.BookScreenContent
import com.example.ojora.ui.screens.home.HomeScreenContent
import com.example.ojora.ui.screens.profile.ProfileScreenContent
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object BookRoute

@Serializable
object ProfileRoute

@Serializable
object DashboardRoute

@Serializable
data class WebViewRoute(
    val title: String,
    val url: String
)

fun NavController.navigateToDashboard(navOptions: NavOptions? = null) {
    navigate(route = DashboardRoute, navOptions = navOptions)
}

fun NavController.navigateToWebView(title: String, url: String, navOptions: NavOptions? = null) {
    navigate(route = WebViewRoute(title = title, url = url), navOptions = navOptions)
}

enum class OjoraTopLevelDestination(
    val icon: ImageVector,
    val label: String
) {
    HOME(Icons.Default.Home, "Home"),
    BOOK(Icons.Default.DateRange, "Book"),
    PROFILE(Icons.Default.AccountCircle, "Profile")
}

fun NavGraphBuilder.dashboardGraph(
    navController: NavController,
    onLogoutClick: () -> Unit,
    onNavigateToFeedDetail: (Int) -> Unit,
    onNavigateToActivity: () -> Unit,
    onNavigateToBookDetail: (String) -> Unit,
    onNavigateToProfileDetail: (String) -> Unit
) {
    composable<DashboardRoute> {
        DashboardScreen(
            onLogoutClick = onLogoutClick,
            onNavigateToFeedDetail = onNavigateToFeedDetail,
            onNavigateToActivity = onNavigateToActivity,
            onNavigateToBookDetail = onNavigateToBookDetail,
            onNavigateToProfileDetail = onNavigateToProfileDetail,
            onNavigateToWebView = { title, url ->
                navController.navigateToWebView(title, url)
            }
        )
    }

    composable<WebViewRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<WebViewRoute>()
        WebViewScreen(
            title = route.title,
            url = route.url,
            onBackClick = { navController.popBackStack() }
        )
    }
}

@Composable
fun DashboardScreen(
    onLogoutClick: () -> Unit,
    onNavigateToFeedDetail: (Int) -> Unit,
    onNavigateToActivity: () -> Unit,
    onNavigateToBookDetail: (String) -> Unit,
    onNavigateToProfileDetail: (String) -> Unit,
    onNavigateToWebView: (String, String) -> Unit
) {
    var selectedDestination by remember { mutableStateOf(OjoraTopLevelDestination.HOME) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                OjoraTopLevelDestination.entries.forEach { destination ->
                    NavigationBarItem(
                        selected = selectedDestination == destination,
                        onClick = { selectedDestination = destination },
                        icon = { Icon(destination.icon, contentDescription = destination.label) },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedDestination) {
                OjoraTopLevelDestination.HOME -> {
                    HomeScreenContent(
                        onFeedPostClick = onNavigateToFeedDetail,
                        onActivityClick = onNavigateToActivity,
                        onCtaClick = { title, url -> onNavigateToWebView(title, url) }
                    )
                }
                OjoraTopLevelDestination.BOOK -> {
                    BookScreenContent(
                        onServiceClick = onNavigateToBookDetail
                    )
                }
                OjoraTopLevelDestination.PROFILE -> {
                    ProfileScreenContent(
                        onProfileDetailClick = onNavigateToProfileDetail,
                        onLogoutClick = onLogoutClick
                    )
                }
            }
        }
    }
}

@Composable
fun WebViewScreen(
    title: String,
    url: String,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Web View: $title", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text("URL: $url", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick) {
            Text("Back")
        }
    }
}
