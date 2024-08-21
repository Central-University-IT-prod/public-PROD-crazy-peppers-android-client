package com.knomster.teamx.presentation.navigation.participant.captain

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.knomster.teamx.presentation.navigationData.Screen

@Composable
fun CaptainTeamsAndTeamNavGraph(
    navHostController: NavHostController,
    captainUsers: @Composable () -> Unit,
    captainDetailUserScreen: @Composable (id: String) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.CaptainUsersScreen.route
    ) {
        composable(Screen.CaptainUsersScreen.route) {
            captainUsers()
        }
        composable(Screen.CaptainDetailUserScreenNotification.route) {
            val id = it.arguments?.getString("id")
            captainDetailUserScreen(id ?: "")
        }
    }
}