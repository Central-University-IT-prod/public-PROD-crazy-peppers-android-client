package com.knomster.teamx.presentation.navigation.participant

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.knomster.teamx.presentation.navigation.participant.captain.CaptainScreen
import com.knomster.teamx.presentation.screens.participant.RegisterScreen
import com.knomster.teamx.presentation.screens.participant.user.UserScreen
import com.knomster.teamx.presentation.uiElements.LoaderScreen
import com.knomster.teamx.presentation.viewModels.MainAppViewModel

@Composable
fun ParticipantScreen(
    mainAppViewModel: MainAppViewModel
) {
    val hasMeInformation by mainAppViewModel.hasMeInformation.observeAsState()
    val meInformation by mainAppViewModel.meInformation.observeAsState()
    if (hasMeInformation == null) {
        mainAppViewModel.getMeInformation()
        LoaderScreen()
    } else {
        val hasOlympiad by mainAppViewModel.hasOlympiad.observeAsState()
        if (hasOlympiad == null) {
            mainAppViewModel.getOlympiad()
            LoaderScreen()
        } else {
            if (meInformation?.name.isNullOrEmpty()) {
                RegisterScreen(mainAppViewModel = mainAppViewModel)
            } else {
                if (meInformation?.isCaptain == true) {
                    CaptainScreen(
                        mainAppViewModel = mainAppViewModel,
                    )
                } else {
                    UserScreen(mainAppViewModel = mainAppViewModel)
                }
            }
        }
    }
}