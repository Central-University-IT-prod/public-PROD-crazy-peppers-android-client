package com.knomster.teamx.presentation.navigation.participant.captain

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.knomster.teamx.presentation.navigationData.Screen

@Composable
fun CaptainNotificationAndDetailUserNavGraph(
    navHostController: NavHostController,
    captainNotifications: @Composable () -> Unit,
    captainDetailUserScreen: @Composable (id: String) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.CaptainNotificationsScreen.route
    ) {
        composable(Screen.CaptainNotificationsScreen.route) {
            captainNotifications()
        }
        composable(Screen.CaptainDetailUserScreen.route) {
            val id = it.arguments?.getString("id")
            captainDetailUserScreen(id ?: "")
        }
    }
}