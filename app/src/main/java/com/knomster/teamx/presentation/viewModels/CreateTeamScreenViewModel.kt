package com.knomster.teamx.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knomster.teamx.domain.entities.RoleWhenCreate
import com.knomster.teamx.domain.entities.TeamForApi
import com.knomster.teamx.presentation.entities.CheckCreateOlympiadResult

class CreateTeamScreenViewModel(val mainAppViewModel: MainAppViewModel) : ViewModel() {

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    private val _description = MutableLiveData("")
    val description: LiveData<String> = _description

    private val _tags = MutableLiveData(emptyList<String>())
    val tags: LiveData<List<String>> = _tags

    private val _roles = MutableLiveData<List<RoleWhenCreate>?>(null)
    val roles: LiveData<List<RoleWhenCreate>?> = _roles

    fun changeName(newValue: String) {
        _name.postValue(newValue)
    }

    fun changeDescription(newValue: String) {
        _description.postValue(newValue)
    }

    fun setDefaultRoles() {
        val roles = mainAppViewModel.olympiad.value?.participants?.roles?.map {
            RoleWhenCreate(
                name = it.name,
                available = it.minParticipants
            )
        }
        _roles.postValue(roles ?: emptyList())
    }

    fun changeRole(roleWhenCreate: RoleWhenCreate) {
        val newRoles = roles.value?.toMutableList() ?: mutableListOf()
        newRoles.replaceAll {
            if (it.name == roleWhenCreate.name) {
                roleWhenCreate
            } else {
                it
            }
        }
        _roles.postValue(newRoles)
    }

    fun changeTags(newValue: List<String>) {
        _tags.postValue(newValue)
    }

    fun checkValues(): CheckCreateOlympiadResult {
        if (name.value.isNullOrEmpty()) {
            return CheckCreateOlympiadResult(
                result = false,
                error = "Пустое название команды"
            )
        }

        var usersSum = 0L
        roles.value?.forEach { role ->
            usersSum += role.available
        }
        if (usersSum !in (mainAppViewModel.olympiad.value?.participants?.min
                ?: 0L)..(mainAppViewModel.olympiad.value?.participants?.max ?: 0L)
        ) {
            return CheckCreateOlympiadResult(
                result = false,
                error = "Неверное количество участников"
            )
        }
        roles.value?.forEach { role ->
            val olympiadRole =
                mainAppViewModel.olympiad.value?.participants?.roles?.find { it.name == role.name }
            if (role.available !in (olympiadRole?.minParticipants
                    ?: 0L)..(olympiadRole?.maxParticipants ?: 0L)
            ) {
                return CheckCreateOlympiadResult(
                    result = false,
                    error = "Неверное количество участников для ${role.name}"
                )
            }
        }
        return CheckCreateOlympiadResult(true)
    }

    fun createTeam(function: () -> Unit) {
        mainAppViewModel.createTeam(
            TeamForApi(
                name = name.value ?: "",
                description = description.value ?: "",
                roles = roles.value ?: emptyList(),
                tags = tags.value ?: emptyList()
            ),
            function = function
        )
    }
}