package com.knomster.teamx.domain.interfaces

import com.knomster.teamx.domain.entities.OlympiadRequest
import com.knomster.teamx.domain.entities.OlympiadResponse
import com.knomster.teamx.domain.entities.forAdmins.CreateOlympiadResult
import com.knomster.teamx.domain.entities.forAdmins.OlympiadDataForAdmins
import com.knomster.teamx.domain.entities.forAdmins.OlympiadDataForApi
import com.knomster.teamx.domain.entities.forParticipants.ParticipantData
import com.knomster.teamx.domain.entities.forParticipants.TeamData
import retrofit2.http.Header

interface AdminApi {

    fun createOlympiad(
        @Header("Authorization") token: String,
        olympiadData: OlympiadRequest
    ): OlympiadResponse?

    fun getOlympiadData(
        @Header("Authorization") token: String
    ): OlympiadResponse?

    fun createUsers(
        @Header("Authorization") token: String,
        count: Long
    ): List<ParticipantData>?

    fun getUsersForAdmin(
        @Header("Authorization") token: String
    ): List<ParticipantData>?

    fun getTeamsForAdmin(): List<TeamData>?
}