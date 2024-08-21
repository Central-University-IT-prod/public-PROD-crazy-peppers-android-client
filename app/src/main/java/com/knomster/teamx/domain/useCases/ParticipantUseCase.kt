package com.knomster.teamx.domain.useCases

import com.knomster.teamx.domain.entities.NotificationData
import com.knomster.teamx.domain.entities.TeamForApi
import com.knomster.teamx.domain.entities.forParticipants.DetailParticipantData
import com.knomster.teamx.domain.entities.forParticipants.DetailTeamData
import com.knomster.teamx.domain.entities.forParticipants.ParticipantData
import com.knomster.teamx.domain.entities.forParticipants.TeamData
import com.knomster.teamx.domain.interfaces.ParticipantApi

class ParticipantUseCase(private val participantApi: ParticipantApi) {
    fun getMe(token: String): DetailParticipantData? {
        return participantApi.getMe(token)
    }

    fun changeMe(token: String, detailParticipantData: DetailParticipantData): DetailParticipantData? {
        return participantApi.changeMe(token, detailParticipantData)
    }

    fun getUserTeams(token: String): List<TeamData> {
        return participantApi.getUserTeams(token) ?: emptyList()
    }

    fun createTeam(
        teamForApi: TeamForApi,
        token: String
    ): DetailTeamData? {
        return participantApi.createTeam(token, teamForApi)
    }

    fun joinToTeam(
        token: String,
        id: String
    ): Boolean {
        return participantApi.joinToTeam(token, id)
    }

    fun inviteToTeam(
        token: String,
        id: String
    ): Boolean {
        return participantApi.inviteToTeam(token, id)
    }

    fun responseNotificationUser(
        id: String,
        action: Boolean
    ): List<NotificationData> {
        return participantApi.responseNotificationUser(id, action)
    }

    fun responseNotificationCaptain(
        id: String,
        action: Boolean
    ): List<NotificationData> {
        return participantApi.responseNotificationCaptain(id, action)
    }

    fun getUsersForCaptain(
        token: String
    ): List<ParticipantData> {
        return participantApi.getUsersForCaptain(token)
    }
}