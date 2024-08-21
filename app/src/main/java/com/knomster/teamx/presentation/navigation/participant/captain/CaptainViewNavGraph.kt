package com.knomster.teamx.presentation.navigation.participant.captain

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.knomster.teamx.presentation.navigationData.Screen

@Composable
fun CaptainViewNavGraph(
    navHostController: NavHostController,
    captainUsersAndUserScreen: @Composable () -> Unit,
    captainTeamScreen: @Composable () -> Unit,
    captainNotificationsScreen: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.CaptainUsersAndUserScreen.route
    ) {
        composable(Screen.CaptainUsersAndUserScreen.route) {
            captainUsersAndUserScreen()
        }
        composable(Screen.CaptainTeamScreen.route) {
            captainTeamScreen()
        }
        composable(Screen.CaptainNotificationsScreen.route) {
            captainNotificationsScreen()
        }
    }
}