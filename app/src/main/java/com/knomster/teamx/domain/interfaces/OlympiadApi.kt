package com.knomster.teamx.domain.interfaces

import com.knomster.teamx.domain.entities.LoginResponse
import com.knomster.teamx.domain.entities.NotificationData
import com.knomster.teamx.domain.entities.forParticipants.DetailParticipantData
import com.knomster.teamx.domain.entities.forParticipants.DetailTeamData

interface OlympiadApi {
    fun signIn(
        login: String,
        password: String
    ): LoginResponse?

    fun getMyTeam(
        token: String
    ): DetailTeamData?

    fun getTeamById(
        id: String
    ): DetailTeamData?

    fun getUserById(
        id: String
    ): DetailParticipantData?

    fun getNotifications(
        token: String
    ): List<NotificationData>
}