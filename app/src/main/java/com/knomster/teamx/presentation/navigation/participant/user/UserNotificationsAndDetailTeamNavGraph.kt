package com.knomster.teamx.presentation.navigation.participant.user

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.knomster.teamx.presentation.navigationData.Screen

@Composable
fun UserNotificationAndDetailNavGraph(
    navHostController: NavHostController,
    userNotifications: @Composable () -> Unit,
    userDetailTeamScreen: @Composable (id: String) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.UserNotificationsScreen.route
    ) {
        composable(Screen.UserNotificationsScreen.route) {
            userNotifications()
        }
        composable(Screen.UserDetailTeamScreenNotification.route) {
            val id = it.arguments?.getString("id")
            userDetailTeamScreen(id ?: "")
        }
    }
}