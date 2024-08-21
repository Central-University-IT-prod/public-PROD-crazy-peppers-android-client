package com.knomster.teamx.presentation.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knomster.teamx.R
import com.knomster.teamx.domain.entities.OlympiadRequest
import com.knomster.teamx.domain.entities.Participants
import com.knomster.teamx.domain.entities.RoleInfo
import com.knomster.teamx.domain.entities.RoleRequst
import com.knomster.teamx.presentation.entities.CheckCreateOlympiadResult
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class CreateOlympiadScreenViewModel(private val mainAppViewModel: MainAppViewModel) : ViewModel() {

    private val _olympiadName = MutableLiveData("")
    val olympiadName: LiveData<String> = _olympiadName

    private val _deadline = MutableLiveData(System.currentTimeMillis() + 1000 * 60 * 60 * 24)
    val deadline: LiveData<Long> = _deadline

    private val _participantsCount = MutableLiveData(2L)
    val participantsCount: LiveData<Long> = _participantsCount


    private val _tags = MutableLiveData(emptyList<String>())
    val tags: LiveData<List<String>> = _tags

    private val _participants =
        MutableLiveData(Participants(min = 2L, max = 5L, roles = emptyList()))
    val participants: LiveData<Participants> = _participants

    fun changeOlympiadName(newValue: String) {
        _olympiadName.postValue(newValue)
    }

    fun changeDeadline(newValue: Long) {
        _deadline.postValue(newValue)
    }

    fun changeParticipantsCount(newValue: Long) {
        _participantsCount.postValue(newValue)
    }

    fun changeRole(oldRole: RoleInfo, newRole: RoleInfo) {
        val newRoles = participants.value?.roles?.toMutableList()
        newRoles?.replaceAll { roleInfo ->
            if (roleInfo == oldRole) {
                newRole
            } else {
                roleInfo
            }
        }
        _participants.postValue(participants.value?.copy(roles = newRoles?.toList() ?: emptyList()))
    }

    fun addRole() {
        val newRoles = participants.value?.roles?.toMutableList()
        newRoles?.add(
            RoleInfo(
                name = "Роль ${(participants.value?.roles?.size ?: 0) + 1}",
                minParticipants = 0,
                maxParticipants = 2
            )
        )
        _participants.postValue(participants.value?.copy(roles = newRoles?.toList() ?: emptyList()))
    }

    fun deleteRole(role: RoleInfo) {
        val newRoles = participants.value?.roles?.toMutableList()
        newRoles?.remove(role)
        _participants.postValue(participants.value?.copy(roles = newRoles?.toList() ?: emptyList()))
    }

    fun changeTag(oldTag: String, newTag: String) {
        val newTags = tags.value?.toMutableList()
        newTags?.replaceAll { tag ->
            if (tag == oldTag) {
                newTag
            } else {
                tag
            }
        }
        _tags.postValue(newTags)
    }

    fun addTag() {
        val newTags = tags.value?.toMutableList()
        newTags?.add("Тег ${(tags.value?.size ?: 0) + 1}")
        _tags.postValue(newTags)
    }

    fun deleteTag(tag: String) {
        val newTags = tags.value?.toMutableList()
        newTags?.remove(tag)
        _tags.postValue(newTags)
    }

    fun setParticipants(min: Long? = null, max: Long? = null) {
        var newParticipants = participants.value
        min?.let { newParticipants = newParticipants?.copy(min = it) }
        max?.let { newParticipants = newParticipants?.copy(max = it) }
        _participants.postValue(newParticipants)
    }

    fun checkValues(context: Context): CheckCreateOlympiadResult {
        if (olympiadName.value.isNullOrEmpty()) {
            return CheckCreateOlympiadResult(
                result = false,
                error = context.resources.getString(R.string.error_empty_olympiad_name)
            )
        }
        if ((deadline.value ?: 0L) < System.currentTimeMillis() - 1000 * 60 * 60 * 24) {
            return CheckCreateOlympiadResult(
                result = false,
                error = context.resources.getString(R.string.error_deadline_time)
            )
        }
        if (participants.value?.roles.isNullOrEmpty()) {
            return CheckCreateOlympiadResult(
                result = false,
                error = context.resources.getString(R.string.error_empty_roles)
            )
        }
        if ((participants.value?.min ?: 0L) > (participants.value?.max ?: 0L)) {
            return CheckCreateOlympiadResult(
                result = false,
                error = context.resources.getString(R.string.error_max_less_than_min)
            )
        }
        participants.value?.roles?.forEach {
            if (it.minParticipants > it.maxParticipants) {
                return CheckCreateOlympiadResult(
                    result = false,
                    error = context.resources.getString(R.string.error_max_less_than_min)
                )
            }
        }
        if ((participantsCount.value ?: 0L) <= 0) {
            return CheckCreateOlympiadResult(
                result = false,
                error = context.resources.getString(R.string.error_too_few_participants)
            )
        }
        return CheckCreateOlympiadResult(true)
    }

    fun createOlympiad() {
        mainAppViewModel.createOlympiad(
            OlympiadRequest(
                name = olympiadName.value ?: "",
                max_participants_in_team = participants.value?.max ?: 0L,
                min_participants_in_team = participants.value?.min ?: 0L,
                deadline = (deadline.value ?: 0L).toDateTimeString(),
                roles = participants.value?.roles?.map { role ->
                    RoleRequst(
                        name = role.name,
                        max_in_team = role.maxParticipants,
                        min_in_team = role.minParticipants,
                    )
                } ?: emptyList(),
                tags = tags.value ?: emptyList()
            ),
            onCreate = {
                mainAppViewModel.setOlympiad(it)
                mainAppViewModel.fillUsers(participantsCount.value ?: 20L)
            }
        )
    }

    private fun Long.toDateTimeString(): String {
        val instant = Instant.ofEpochMilli(this)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter)
    }
}