package com.knomster.teamx.domain.useCases

import com.knomster.teamx.domain.entities.LoginResponse
import com.knomster.teamx.domain.entities.NotificationData
import com.knomster.teamx.domain.entities.forParticipants.DetailParticipantData
import com.knomster.teamx.domain.entities.forParticipants.DetailTeamData
import com.knomster.teamx.domain.interfaces.OlympiadApi

class OlympiadUseCase(private val olympiadApi: OlympiadApi) {
    fun signIn(
        login: String,
        password: String
    ): LoginResponse? {
        return olympiadApi.signIn(login, password)
    }

    fun getMyTeam(
        token: String
    ): DetailTeamData? {
        return olympiadApi.getMyTeam(token)
    }

    fun getTeamById(
        id: String
    ): DetailTeamData? {
        return olympiadApi.getTeamById(id)
    }

    fun getUserById(
        id: String
    ): DetailParticipantData? {
        return olympiadApi.getUserById(id)
    }

    fun getNotifications(
        token: String
    ): List<NotificationData> {
        return olympiadApi.getNotifications(token)
    }
}