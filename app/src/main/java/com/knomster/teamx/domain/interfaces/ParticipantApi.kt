package com.knomster.teamx.domain.interfaces

import com.knomster.teamx.domain.entities.NotificationData
import com.knomster.teamx.domain.entities.TeamForApi
import com.knomster.teamx.domain.entities.forParticipants.DetailParticipantData
import com.knomster.teamx.domain.entities.forParticipants.DetailTeamData
import com.knomster.teamx.domain.entities.forParticipants.ParticipantData
import com.knomster.teamx.domain.entities.forParticipants.TeamData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ParticipantApi {

    fun getMe(
        @Header("Authorization") token: String
    ): DetailParticipantData?

    fun changeMe(
        @Header("Authorization") token: String,
        detailParticipantData: DetailParticipantData
    ): DetailParticipantData?

    fun getUserTeams(
        @Header("Authorization") token: String,
    ): List<TeamData>?

    fun joinToTeam(
        @Header("Authorization") token: String,
        teamId: String
    ): Boolean

    fun inviteToTeam(
        @Header("Authorization") token: String,
        userId: String
    ): Boolean

    fun createTeam(
        @Header("Authorization") token: String,
        teamForApi: TeamForApi
    ): DetailTeamData?

    fun responseNotificationUser(
        id: String,
        action: Boolean
    ): List<NotificationData>

    fun responseNotificationCaptain(
        id: String,
        action: Boolean
    ): List<NotificationData>

    fun getUsersForCaptain(
        @Header("Authorization") token: String
    ): List<ParticipantData>
}