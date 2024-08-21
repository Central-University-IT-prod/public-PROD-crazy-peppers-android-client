package com.knomster.teamx.presentation.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.knomster.teamx.data.AdminApiImpl
import com.knomster.teamx.data.OlympiadApiImpl
import com.knomster.teamx.data.ParticipantApiImpl
import com.knomster.teamx.domain.entities.NotificationData
import com.knomster.teamx.domain.entities.OlympiadRequest
import com.knomster.teamx.domain.entities.TeamForApi
import com.knomster.teamx.domain.entities.forParticipants.DetailParticipantData
import com.knomster.teamx.domain.entities.forParticipants.DetailTeamData
import com.knomster.teamx.domain.entities.forParticipants.OlympiadData
import com.knomster.teamx.domain.entities.forParticipants.ParticipantData
import com.knomster.teamx.domain.entities.forParticipants.TeamData
import com.knomster.teamx.domain.useCases.AdminUseCase
import com.knomster.teamx.domain.useCases.OlympiadUseCase
import com.knomster.teamx.domain.useCases.ParticipantUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainAppViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences =
        application.getSharedPreferences("settings", Context.MODE_PRIVATE)
    private val _token = MutableLiveData<String?>(null)
    val token: LiveData<String?> = _token
    private val _isAdmin = MutableLiveData<Boolean?>(null)
    val isAdmin: LiveData<Boolean?> = _isAdmin

    private val _hasOlympiad = MutableLiveData<Boolean?>(null)
    val hasOlympiad: LiveData<Boolean?> = _hasOlympiad
    private val _olympiad = MutableLiveData<OlympiadData?>(null)
    val olympiad: LiveData<OlympiadData?> = _olympiad

    private val _usersForAdmin = MutableLiveData<List<ParticipantData>?>(null)
    val usersForAdmin: LiveData<List<ParticipantData>?> = _usersForAdmin
    private val _hasUsersForAdmin = MutableLiveData<Boolean?>(null)
    val hasUsersForAdmin: LiveData<Boolean?> = _hasUsersForAdmin

    private val _usersForCaptain = MutableLiveData<List<ParticipantData>?>(null)
    val usersForCaptain: LiveData<List<ParticipantData>?> = _usersForCaptain
    private val _hasUsersForCaptain = MutableLiveData<Boolean?>(null)
    val hasUsersForCaptain: LiveData<Boolean?> = _hasUsersForCaptain

    private val _teamsForAdmin = MutableLiveData<List<TeamData>?>(null)
    val teamsForAdmin: LiveData<List<TeamData>?> = _teamsForAdmin
    private val _hasTeamsForAdmin = MutableLiveData<Boolean?>(null)
    val hasTeamsForAdmin: LiveData<Boolean?> = _hasTeamsForAdmin

    private val _meInformation = MutableLiveData<DetailParticipantData?>(null)
    val meInformation: LiveData<DetailParticipantData?> = _meInformation
    private val _hasMeInformation = MutableLiveData<Boolean?>(null)
    val hasMeInformation: LiveData<Boolean?> = _hasMeInformation

    private val participantApi = ParticipantApiImpl()
    private val adminApi = AdminApiImpl()
    private val olympiadApi = OlympiadApiImpl()
    private val olympiadUseCase = OlympiadUseCase(olympiadApi)
    private val participantUseCase = ParticipantUseCase(participantApi)
    private val adminUseCase = AdminUseCase(adminApi)

    // For user

    private val _userTeams = MutableLiveData<List<TeamData>>(emptyList())
    val userTeams: LiveData<List<TeamData>> = _userTeams
    private val _hasUserTeams = MutableLiveData<Boolean?>(null)
    val hasUserTeams: LiveData<Boolean?> = _hasUserTeams

    // My team

    private val _myTeam = MutableLiveData<DetailTeamData?>(null)
    val myTeam: LiveData<DetailTeamData?> = _myTeam
    private val _hasMyTeam = MutableLiveData<Boolean?>(null)
    val hasMyTeam: LiveData<Boolean?> = _hasMyTeam

    private val _notifications = MutableLiveData<List<NotificationData>>(emptyList())
    val notifications: LiveData<List<NotificationData>> = _notifications
    private val _hasNotifications = MutableLiveData<Boolean?>(null)
    val hasNotifications: LiveData<Boolean?> = _hasNotifications

    fun getDataFromPreferences() {
        if (sharedPreferences.contains("token")) {
            _token.postValue(sharedPreferences.getString("token", ""))
            _isAdmin.postValue(sharedPreferences.getBoolean("isAdmin", false))
        } else {
            _token.postValue("")
        }
    }

    fun signIn(login: String, password: String, onLogin: (Boolean) -> Unit) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val loginResponse = olympiadUseCase.signIn(login, password)
                onLogin(loginResponse != null)
                if (loginResponse == null) {
                    onLogin(false)
                } else {
                    _token.postValue(loginResponse.token)
                    _isAdmin.postValue(loginResponse.isAdmin)
                    val editor = sharedPreferences.edit()
                    editor.putString("token", loginResponse.token)
                    editor.putBoolean("isAdmin", loginResponse.isAdmin)
                    editor.apply()
                    onLogin(true)
                }
            }
        }

    fun createOlympiad(olympiad: OlympiadRequest, onCreate: (OlympiadData?) -> Unit) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = adminUseCase.createOlympiad(
                    token = token.value ?: "",
                    olympiadData = olympiad
                )
                onCreate(result)
            }
        }

    fun createTeam(teamForApi: TeamForApi, function: () -> Unit) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = participantUseCase.createTeam(
                    token = token.value ?: "",
                    teamForApi = teamForApi
                )
                _myTeam.postValue(result)
                _hasMyTeam.postValue(result != null)
                function()
            }
        }

    fun getOlympiad() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val olympiadData = adminUseCase.getOlympiadData(token.value ?: "")
            if (olympiadData == null) {
                _hasOlympiad.postValue(false)
            } else {
                _olympiad.postValue(olympiadData)
                _hasOlympiad.postValue(true)
            }
        }
    }

    fun getUsersForAdmin() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val usersForAdmin = adminUseCase.getUsersForAdmin(token.value ?: "")
            _usersForAdmin.postValue(usersForAdmin)
            _hasUsersForAdmin.postValue(true)
        }
    }

    fun getUsersForCaptain() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val usersForCaptain = participantUseCase.getUsersForCaptain(token.value ?: "")
            _usersForCaptain.postValue(usersForCaptain)
            _hasUsersForCaptain.postValue(true)
        }
    }

    fun getTeamsForAdmin() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val teamsForAdmin = adminUseCase.getTeamsForAdmin()
            _teamsForAdmin.postValue(teamsForAdmin)
            _hasTeamsForAdmin.postValue(true)
        }
    }

    fun setOlympiad(olympiadData: OlympiadData?) {
        if (olympiadData == null) {
            _hasOlympiad.postValue(false)
        } else {
            _olympiad.postValue(olympiadData)
            _hasOlympiad.postValue(true)
        }
    }

    fun getMeInformation() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val usersForAdmin = participantUseCase.getMe(token.value ?: "")
            _meInformation.postValue(usersForAdmin)
            _hasMeInformation.postValue(true)
        }
    }

    fun changeMe(detailParticipantData: DetailParticipantData) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val response = participantUseCase.changeMe(token.value ?: "", detailParticipantData)
            _meInformation.postValue(response)
            _hasMeInformation.postValue(true)
        }
    }

    fun getUserTeams() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val response = participantUseCase.getUserTeams(token.value ?: "")
            _userTeams.postValue(response)
            _hasUserTeams.postValue(true)
        }
    }

    fun getMyTeam() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val response = olympiadUseCase.getMyTeam(token.value ?: "")
            _myTeam.postValue(response)
            _hasMyTeam.postValue(response != null)
        }
    }

    fun fillUsers(count: Long) {
        adminApi.createUsers(
            token = token.value ?: "",
            count = count
        )
    }

    fun joinToTeam(id: String, onSend: (Boolean) -> Unit) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val response = participantUseCase.joinToTeam(token.value ?: "", id)
            onSend(response)
        }
    }

    fun getNotifications() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val response = olympiadUseCase.getNotifications(token.value ?: "")
            _hasNotifications.postValue(true)
            _notifications.postValue(response)
        }
    }

    fun responseNotificationUser(id: String, action: Boolean) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val response = participantUseCase.responseNotificationUser(id, action = action)
            _hasNotifications.postValue(true)
            _notifications.postValue(response)
        }
    }

    fun responseNotificationCaptain(id: String, action: Boolean) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val response = participantUseCase.responseNotificationCaptain(id, action = action)
            _hasNotifications.postValue(true)
            _notifications.postValue(response)
        }
    }

    fun inviteToTeam(id: String, onSend: (Boolean) -> Unit) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val response = participantUseCase.inviteToTeam(token.value ?: "", id)
            onSend(response)
        }
    }

    fun updateAllData() {
        getUsersForAdmin()
        getUsersForCaptain()
        getTeamsForAdmin()
        getMeInformation()
        getUserTeams()
        getMyTeam()
        getNotifications()
    }
}