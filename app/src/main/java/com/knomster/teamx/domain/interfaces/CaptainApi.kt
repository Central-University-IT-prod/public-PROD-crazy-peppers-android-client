package com.knomster.teamx.domain.interfaces

import com.knomster.teamx.domain.entities.forParticipants.CaptainNotification
import com.knomster.teamx.domain.entities.forParticipants.NewTeams
import com.knomster.teamx.domain.entities.forParticipants.TeamData
import retrofit2.http.Header

interface CaptainApi {

    fun createTeams(
        @Header("Authorization") token: String,
        newTeams: NewTeams
    ): TeamData

    fun changeTeams(
        @Header("Authorization") token: String,
        teamData: TeamData
    ): TeamData

    fun getNotifications(
        @Header("Authorization") token: String
    ): CaptainNotification

    fun inviteToTeam(
        @Header("Authorization") token: String,
        participantId: String
    ): Boolean

    fun deleteTeam(
        @Header("Authorization") token: String
    ): Boolean

}