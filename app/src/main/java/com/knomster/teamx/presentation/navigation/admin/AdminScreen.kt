package com.knomster.teamx.presentation.navigation.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.knomster.teamx.presentation.navigationData.Screen
import com.knomster.teamx.presentation.navigationData.rememberNavigationState
import com.knomster.teamx.presentation.screens.admin.CreateOlympiadScreen
import com.knomster.teamx.presentation.uiElements.LoaderScreen
import com.knomster.teamx.presentation.viewModels.MainAppViewModel

@Composable
fun AdminScreen(
    mainAppViewModel: MainAppViewModel
) {
    val hasOlympiad by mainAppViewModel.hasOlympiad.observeAsState()
    if (hasOlympiad == null) {
        mainAppViewModel.getOlympiad()
        LoaderScreen()
    } else {
        val navigationState = rememberNavigationState()
        AdminNavGraph(
            navHostController = navigationState.navHostController,
            startDestination = if (hasOlympiad == false) {
                Screen.CreateOlympiadScreen.route
            } else Screen.AdminViewScreen.route,
            createOlympiadScreen = {
                CreateOlympiadScreen(
                    mainAppViewModel = mainAppViewModel,
                    onBack = {
                        navigationState.navHostController.popBackStack()
                    }
                )
            },
            adminViewScreen = {
                AdminViewScreen(mainAppViewModel = mainAppViewModel)
            }
        )
    }
}