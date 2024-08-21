package com.knomster.teamx.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreateTeamScreenViewModelFactory(private val mainAppViewModel: MainAppViewModel): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateTeamScreenViewModel(mainAppViewModel) as T
    }
}