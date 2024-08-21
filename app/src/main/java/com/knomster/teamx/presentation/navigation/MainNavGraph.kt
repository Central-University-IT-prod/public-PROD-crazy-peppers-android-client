package com.knomster.teamx.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.knomster.teamx.presentation.navigationData.Screen

@Composable
fun MainNavGraph(
    navHostController: NavHostController,
    startDestination: String,
    loginScreen: @Composable () -> Unit,
    adminScreen: @Composable () -> Unit,
    participantScreen: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(Screen.LoginScreen.route) {
            loginScreen()
        }
        composable(
            route = Screen.AdminScreen.route,
        ) {
            adminScreen()
        }
        composable(
            route = Screen.ParticipantScreen.route,
        ) {
            participantScreen()
        }
    }
}