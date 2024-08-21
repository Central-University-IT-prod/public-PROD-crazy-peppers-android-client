package com.knomster.teamx.presentation.screens.participant

import androidx.compose.runtime.Composable
import com.knomster.teamx.presentation.screens.DetailTeamScreen
import com.knomster.teamx.presentation.viewModels.MainAppViewModel

@Composable
fun MyTeamScreen(
    mainAppViewModel: MainAppViewModel,
    teamId: String,
    onBack: () -> Unit
) {
    DetailTeamScreen(
        mainAppViewModel = mainAppViewModel,
        teamId = teamId,
        joinButton = false,
        onBack = onBack
    )
}