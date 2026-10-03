package com.example.ojora.ui.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.ojora.ui.screens.book.bookGraph
import com.example.ojora.ui.screens.book.navigateToBookDetail
import com.example.ojora.ui.screens.dashboard.DashboardRoute
import com.example.ojora.ui.screens.dashboard.dashboardGraph
import com.example.ojora.ui.screens.dashboard.navigateToWebView
import com.example.ojora.ui.screens.home.homeGraph
import com.example.ojora.ui.screens.home.navigateToActivitySection
import com.example.ojora.ui.screens.home.navigateToFeedDetail
import com.example.ojora.ui.screens.login.LoginRoute
import com.example.ojora.ui.screens.login.loginGraph
import com.example.ojora.ui.screens.profile.navigateToProfileDetail
import com.example.ojora.ui.screens.profile.profileGraph

/**
 * App-level Navigation Host defining the root navigation graph.
 *
 * Consists of the Login, Dashboard, and feature subgraphs.
 * Global slide-in/slide-out transitions are applied to all screens.
 *
 * @param navController NavHostController responsible for screen routing operations.
 * @param startDestination Type-safe startup route computed dynamically based on authentication state.
 * @param onLoginSuccess Callback triggered when user signs in, directing user to Dashboard.
 * @param onLogoutClick Callback triggered when user signs out, directing user to Login.
 * @param modifier Composable layout modifier passed from parent layout.
 */
@Composable
fun OjoraNavHost(
    navController: NavHostController,
    startDestination: Any,
    onLoginSuccess: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) {

        /**
         * Login & Welcome subgraph
         */
         loginGraph(
             navController = navController,
             onLoginSuccess = onLoginSuccess

         )

        /**
         * Main Dashboard subgraph with top-level tabs
         */
        dashboardGraph(
            navController = navController,
            onLogoutClick = onLogoutClick,
            onNavigateToFeedDetail = { index -> navController.navigateToFeedDetail(index) },
            onNavigateToActivity = { navController.navigateToActivitySection() },
            onNavigateToBookDetail = { serviceId -> navController.navigateToBookDetail(serviceId) },
            onNavigateToProfileDetail = { section -> navController.navigateToProfileDetail(section) }
        )

        /**
         * Home detail subgraph
         */
        homeGraph(
            navController = navController,
            onCtaClick = { title, url -> navController.navigateToWebView(title, url) }
        )

        /**
         * Booking detail subgraph
         */
        bookGraph(
            navController = navController
        )

        /**
         * Profile detail subgraph
         */
        profileGraph(
            navController = navController
        )
    }
}
