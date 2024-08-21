package com.knomster.teamx.presentation.navigation.participant.user

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.knomster.teamx.presentation.navigationData.Screen

@Composable
fun UserTeamsAndTeamNavGraph(
    navHostController: NavHostController,
    userTeams: @Composable () -> Unit,
    userDetailTeamScreen: @Composable (id: String) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.UserTeamsScreen.route
    ) {
        composable(Screen.UserTeamsScreen.route) {
            userTeams()
        }
        composable(Screen.UserDetailTeamScreen.route) {
            val id = it.arguments?.getString("id")
            userDetailTeamScreen(id ?: "")
        }
    }
}