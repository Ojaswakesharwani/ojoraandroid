package com.example.ojora.task

import android.content.Intent
import com.example.ojora.ui.screens.home.ActivityScreenRoute
import com.example.ojora.ui.screens.home.FeedDetailRoute

/**
 * Resolver for deep links and push notification redirection targets.
 */
class
RedirectionResolver {
    var pendingRoute: Any? = null
        private set

    fun resolveAndSetRedirection(intent: Intent?) {
        val destination = intent?.getStringExtra("EXTRA_DESTINATION")
        pendingRoute = when (destination) {
            "FEED" -> FeedDetailRoute(index = intent.getIntExtra("EXTRA_FEED_INDEX", 0))
            "ACTIVITY" -> ActivityScreenRoute
            else -> null
        }
    }

    fun consumePendingRoute(): Any? {
        val route = pendingRoute
        pendingRoute = null
        return route
    }

    fun shouldKeepSplashScreen(intent: Intent?, isUserLoggedIn: Boolean?): Boolean {
        return isUserLoggedIn == null
    }
}
