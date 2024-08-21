package com.knomster.teamx.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreateOlympiadViewModelFactory(private val mainAppViewModel: MainAppViewModel): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateOlympiadScreenViewModel(mainAppViewModel) as T
    }
}