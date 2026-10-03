package com.example.ojora.notification

import androidx.navigation.NavController
import com.example.ojora.ui.screens.dashboard.DashboardRoute
import com.example.ojora.ui.screens.home.ActivityScreenRoute
import com.example.ojora.ui.screens.home.FeedDetailRoute

/**
 * Unified notification router that resets the backstack to DashboardRoute
 * before dispatching navigation to the notification destination.
 */
object NotificationRouter {

    fun navigate(navController: NavController, data: Map<String, String>) {
        if (data.isEmpty()) return

        val targetType = data["resourceType"] ?: return

        // Clear backstack to DashboardRoute before navigating to target notification destination
        navController.popBackStack(DashboardRoute, inclusive = false)

        when (targetType) {
            "FEED_POST" -> {
                val index = data["postIndex"]?.toIntOrNull() ?: 0
                navController.navigate(FeedDetailRoute(index = index))
            }
            "ACTIVITY" -> {
                navController.navigate(ActivityScreenRoute)
            }
        }
    }

    fun refreshResourceTypeFor(data: Map<String, String>, isSilent: Boolean): String? {
        return data["resourceType"]
    }
}
