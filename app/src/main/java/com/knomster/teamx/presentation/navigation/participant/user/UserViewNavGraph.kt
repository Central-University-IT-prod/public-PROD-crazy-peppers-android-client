package com.knomster.teamx.presentation.navigation.participant.user

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.knomster.teamx.presentation.navigationData.Screen

@Composable
fun UserViewNavGraph(
    navHostController: NavHostController,
    userTeamsAndTeamScreen: @Composable () -> Unit,
    userTeamScreen: @Composable () -> Unit,
    userNotificationsScreen: @Composable () -> Unit,
    createTeamScreen: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.UserTeamsAndTeamScreen.route
    ) {
        composable(Screen.UserTeamScreen.route) {
            userTeamScreen()
        }
        composable(Screen.UserNotificationsScreen.route) {
            userNotificationsScreen()
        }
        composable(Screen.CreateTeamScreen.route) {
            createTeamScreen()
        }
        composable(Screen.UserTeamsAndTeamScreen.route) {
            userTeamsAndTeamScreen()
        }
    }
}