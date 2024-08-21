package com.knomster.teamx.presentation.navigationData

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationState(
    val navHostController: NavHostController
) {
    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            launchSingleTop = true
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
        }
    }

    fun navigateToUserDetailTeamScreen(id: String) {
        navHostController.navigate(Screen.UserDetailTeamScreen.getRouteWithArgs(id)) {
            launchSingleTop = true
        }
    }

    fun navigateToUserDetailTeamScreenNotification(id: String) {
        navHostController.navigate(Screen.UserDetailTeamScreenNotification.getRouteWithArgs(id)) {
            launchSingleTop = true
        }
    }

    fun navigateToCaptainDetailUserScreen(id: String) {
        navHostController.navigate(Screen.CaptainDetailUserScreen.getRouteWithArgs(id)) {
            launchSingleTop = true
        }
    }

    fun navigateToCaptainDetailUserScreenNotification(id: String) {
        navHostController.navigate(Screen.CaptainDetailUserScreenNotification.getRouteWithArgs(id)) {
            launchSingleTop = true
        }
    }

    fun navigateToAdminDetailTeamScreen(id: String) {
        navHostController.navigate(Screen.AdminDetailTeamScreen.getRouteWithArgs(id)) {
            launchSingleTop = true
        }
    }

    fun navigateToAdminDetailUserScreen(id: String) {
        navHostController.navigate(Screen.AdminDetailUserScreen.getRouteWithArgs(id)) {
            launchSingleTop = true
        }
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
) = remember {
    NavigationState(navHostController)
}