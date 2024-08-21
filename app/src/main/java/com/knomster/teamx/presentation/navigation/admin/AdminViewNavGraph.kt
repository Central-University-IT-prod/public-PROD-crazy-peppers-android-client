package com.knomster.teamx.presentation.navigation.admin

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.knomster.teamx.presentation.navigationData.Screen

@Composable
fun AdminViewNavGraph(
    navHostController: NavHostController,
    adminTeamsScreen: @Composable () -> Unit,
    adminParticipantsScreen: @Composable () -> Unit,
    detailTeamScreen: @Composable (String) -> Unit,
    detailUserScreen: @Composable (String) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.AdminParticipantsScreen.route
    ) {
        composable(Screen.AdminParticipantsScreen.route) {
            adminParticipantsScreen()
        }
        composable(Screen.AdminTeamsScreen.route) {
            adminTeamsScreen()
        }
        composable(Screen.AdminDetailTeamScreen.route) {
            val id = it.arguments?.getString("id")
            if (id != null) detailTeamScreen(id)
        }
        composable(Screen.AdminDetailUserScreen.route) {
            val id = it.arguments?.getString("id")
            if (id != null) detailUserScreen(id)
        }
    }
}