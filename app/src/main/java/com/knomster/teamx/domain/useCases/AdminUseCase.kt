package com.knomster.teamx.domain.useCases

import com.knomster.teamx.domain.entities.OlympiadRequest
import com.knomster.teamx.domain.entities.OlympiadResponse
import com.knomster.teamx.domain.entities.Participants
import com.knomster.teamx.domain.entities.RoleInfo
import com.knomster.teamx.domain.entities.forAdmins.CreateOlympiadResult
import com.knomster.teamx.domain.entities.forAdmins.OlympiadDataForAdmins
import com.knomster.teamx.domain.entities.forAdmins.OlympiadDataForApi
import com.knomster.teamx.domain.entities.forParticipants.OlympiadData
import com.knomster.teamx.domain.entities.forParticipants.ParticipantData
import com.knomster.teamx.domain.entities.forParticipants.TeamData
import com.knomster.teamx.domain.interfaces.AdminApi
import com.knomster.teamx.domain.interfaces.OlympiadApi
import retrofit2.http.Header
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class AdminUseCase(private val adminApi: AdminApi) {

    fun createOlympiad(
        olympiadData: OlympiadRequest,
        token: String
    ): OlympiadData? {
        val response = adminApi.createOlympiad(token, olympiadData) ?: return null
        return OlympiadData(
            name = response.name,
            tags = response.tags,
            deadlineDate = response.deadline.toDate(),
            participants = Participants(
                max = response.maxParticipants,
                min = response.minParticipants,
                roles = response.roles.map { role ->
                    RoleInfo(
                        name = role.name,
                        minParticipants = role.min,
                        maxParticipants = role.max
                    )
                }
            )
        )
    }

    fun getOlympiadData(token: String): OlympiadData? {
        val response = adminApi.getOlympiadData(token) ?: return null
        return OlympiadData(
            name = response.name,
            tags = response.tags,
            deadlineDate = response.deadline.toDate(),
            participants = Participants(
                max = response.maxParticipants,
                min = response.minParticipants,
                roles = response.roles.map { role ->
                    RoleInfo(
                        name = role.name,
                        minParticipants = role.min,
                        maxParticipants = role.max
                    )
                }
            )
        )
    }

    fun createUsers(
        token: String,
        count: Long
    ) {
        adminApi.createUsers(token, count)
    }

    fun getUsersForAdmin(token: String): List<ParticipantData> {
        return adminApi.getUsersForAdmin(token) ?: emptyList()
    }

    fun getTeamsForAdmin(): List<TeamData> {
        return adminApi.getTeamsForAdmin() ?: emptyList()
    }

    private fun String.toDate(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val localDateTime = LocalDateTime.parse(this, formatter)
        return localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault()))
    }

}