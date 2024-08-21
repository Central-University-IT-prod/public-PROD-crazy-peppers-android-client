package com.knomster.teamx.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knomster.teamx.data.OlympiadApiImpl
import com.knomster.teamx.domain.entities.forParticipants.DetailTeamData
import com.knomster.teamx.domain.useCases.OlympiadUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailTeamScreenViewModel(private val mainAppViewModel: MainAppViewModel): ViewModel() {
    private val olympiadApi = OlympiadApiImpl()
    private val olympiadUseCase = OlympiadUseCase(olympiadApi)

    private val _team = MutableLiveData<DetailTeamData?>(null)
    val team: LiveData<DetailTeamData?> = _team
    private val _hasTeamData = MutableLiveData<Boolean?>(null)
    val hasTeamData: LiveData<Boolean?> = _hasTeamData

    fun getTeamData(id: String) = viewModelScope.launch{
        withContext(Dispatchers.IO) {
            val data = olympiadUseCase.getTeamById(id)
            _team.postValue(data)
            _hasTeamData.postValue(data != null)
        }
    }
}