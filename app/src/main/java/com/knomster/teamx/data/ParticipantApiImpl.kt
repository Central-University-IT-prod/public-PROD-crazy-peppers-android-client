package com.knomster.teamx.data

import com.google.gson.Gson
import com.knomster.teamx.domain.Constants
import com.knomster.teamx.domain.entities.NotificationData
import com.knomster.teamx.domain.entities.TeamForApi
import com.knomster.teamx.domain.entities.forParticipants.DetailParticipantData
import com.knomster.teamx.domain.entities.forParticipants.DetailTeamData
import com.knomster.teamx.domain.entities.forParticipants.ParticipantData
import com.knomster.teamx.domain.entities.forParticipants.TeamData
import com.knomster.teamx.domain.interfaces.ParticipantApi
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray

class ParticipantApiImpl : ParticipantApi {
    override fun getMe(token: String): DetailParticipantData? {
        try {
            val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
            val olympiadService = client.create(OlympiadService::class.java)
            val call = olympiadService.getMe("Bearer $token")
            val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return null
                return Gson().fromJson(data, DetailParticipantData::class.java)
            }
            return null
        } catch (_: Exception) {
            return null
        }
    }

    override fun changeMe(
        token: String,
        detailParticipantData: DetailParticipantData
    ): DetailParticipantData? {
        try {
            val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
            val olympiadService = client.create(OlympiadService::class.java)
            val json = Gson().toJson(detailParticipantData)
            val call = olympiadService.changeMe(
                authorization = "Bearer $token",
                body = RequestBody.create(
                    MediaType.parse("application/json"),
                    json
                )
            )
            val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return null
                return Gson().fromJson(data, DetailParticipantData::class.java)
            }
            return null
        } catch (_: Exception) {
            return null
        }
    }

    override fun getUserTeams(token: String): List<TeamData>? {
        try {
            val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
            val olympiadService = client.create(OlympiadService::class.java)
            val call = olympiadService.getTeamsForUser(
                authorization = "Bearer $token"
            )
            val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return null
                val jsonArray = JSONArray(data)
                val results = mutableListOf<TeamData>()
                val gson = Gson()
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val myData = gson.fromJson(jsonObject.toString(), TeamData::class.java)
                    results.add(myData)
                }
                return results
            }
            return null
        } catch (_: Exception) {
            return null
        }
    }

    override fun joinToTeam(token: String, teamId: String): Boolean {
        return try {
            val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
            val olympiadService = client.create(OlympiadService::class.java)
            val call = olympiadService.joinToTeam(
                authorization = "Bearer $token",
                id = teamId
            )
            val response = call.execute()
            response.isSuccessful
        } catch (_: Exception) {
            false
        }
    }

    override fun inviteToTeam(token: String, userId: String): Boolean {
        return try {
            val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
            val olympiadService = client.create(OlympiadService::class.java)
            val call = olympiadService.inviteToTeam(
                authorization = "Bearer $token",
                id = userId
            )
            val response = call.execute()
            response.isSuccessful
        } catch (_: Exception) {
            false
        }
    }

    override fun createTeam(token: String, teamForApi: TeamForApi): DetailTeamData? {
        try {
            val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
            val olympiadService = client.create(OlympiadService::class.java)
            val json = Gson().toJson(teamForApi)
            val call = olympiadService.createTeam(
                authorization = "Bearer $token",
                body = RequestBody.create(
                    MediaType.parse("application/json"),
                    json
                )
            )
            val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return null
                return Gson().fromJson(data, DetailTeamData::class.java)
            }
            return null
        } catch (_: Exception) {
            return null
        }
    }

    override fun responseNotificationUser(id: String, action: Boolean): List<NotificationData> {
        try {
            val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
            val olympiadService = client.create(OlympiadService::class.java)
            val call = olympiadService.responseNotificationUser(
                id = id,
                action = if (action) 1 else 0
            )
            val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return emptyList()
                val jsonArray = JSONArray(data)
                val results = mutableListOf<NotificationData>()
                val gson = Gson()
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val myData = gson.fromJson(jsonObject.toString(), NotificationData::class.java)
                    results.add(myData)
                }
                return results
            }
            return emptyList()
        } catch (_: Exception) {
            return emptyList()
        }
    }

    override fun responseNotificationCaptain(id: String, action: Boolean): List<NotificationData> {
        try {
            val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
            val olympiadService = client.create(OlympiadService::class.java)
            val call = olympiadService.responseNotificationCaptain(
                id = id,
                action = if (action) 1 else 0
            )
            val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return emptyList()
                val jsonArray = JSONArray(data)
                val results = mutableListOf<NotificationData>()
                val gson = Gson()
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val myData = gson.fromJson(jsonObject.toString(), NotificationData::class.java)
                    results.add(myData)
                }
                return results
            }
            return emptyList()
        } catch (_: Exception) {
            return emptyList()
        }
    }

    override fun getUsersForCaptain(token: String): List<ParticipantData> {
        try {
            val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
            val olympiadService = client.create(OlympiadService::class.java)
            val call = olympiadService.getUsersForCaptain(
                authorization = "Bearer $token"
            )
            val response = call.execute()

            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return emptyList()
                val jsonArray = JSONArray(data)
                val results = mutableListOf<ParticipantData>()
                val gson = Gson()
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val myData = gson.fromJson(jsonObject.toString(), ParticipantData::class.java)
                    results.add(myData)
                }
                return results
            }
            return emptyList()
        } catch (_: Exception) {
            return emptyList()
        }
    }
}