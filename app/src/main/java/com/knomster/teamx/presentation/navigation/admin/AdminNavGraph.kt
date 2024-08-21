package com.knomster.teamx.presentation.navigation.admin

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.knomster.teamx.presentation.navigationData.Screen

@Composable
fun AdminNavGraph(
    navHostController: NavHostController,
    startDestination: String,
    createOlympiadScreen: @Composable () -> Unit,
    adminViewScreen: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(Screen.CreateOlympiadScreen.route) {
            createOlympiadScreen()
        }
        composable(Screen.AdminViewScreen.route) {
            adminViewScreen()
        }
    }
}