package com.knomster.teamx.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knomster.teamx.data.OlympiadApiImpl
import com.knomster.teamx.domain.entities.forParticipants.DetailParticipantData
import com.knomster.teamx.domain.useCases.OlympiadUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailParticipantViewModel(private val mainAppViewModel: MainAppViewModel): ViewModel() {
    private val olympiadApi = OlympiadApiImpl()
    private val olympiadUseCase = OlympiadUseCase(olympiadApi)

    private val _user = MutableLiveData<DetailParticipantData?>(null)
    val user: LiveData<DetailParticipantData?> = _user
    private val _hasUserData = MutableLiveData<Boolean?>(null)
    val hasUserData: LiveData<Boolean?> = _hasUserData

    fun getUserData(id: String) = viewModelScope.launch{
        withContext(Dispatchers.IO) {
            val data = olympiadUseCase.getUserById(id)
            _user.postValue(data)
            _hasUserData.postValue(data != null)
        }
    }
}