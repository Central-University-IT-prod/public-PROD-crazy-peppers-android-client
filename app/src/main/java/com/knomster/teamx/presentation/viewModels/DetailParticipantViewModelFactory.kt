package com.knomster.teamx.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailParticipantViewModelFactory(private val mainAppViewModel: MainAppViewModel): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailParticipantViewModel(mainAppViewModel) as T
    }
}