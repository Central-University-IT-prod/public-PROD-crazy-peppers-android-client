package com.knomster.teamx.data

import com.google.gson.Gson
import com.knomster.teamx.data.entities.LoginRequest
import com.knomster.teamx.domain.Constants
import com.knomster.teamx.domain.entities.LoginResponse
import com.knomster.teamx.domain.entities.NotificationData
import com.knomster.teamx.domain.entities.forParticipants.DetailParticipantData
import com.knomster.teamx.domain.entities.forParticipants.DetailTeamData
import com.knomster.teamx.domain.interfaces.OlympiadApi
import org.json.JSONArray

class OlympiadApiImpl : OlympiadApi {
    override fun signIn(login: String, password: String): LoginResponse? {
        try {
            val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
            val olympiadService = client.create(OlympiadService::class.java)
            val call = olympiadService.signIn(
                LoginRequest(
                    login = login,
                    password = password
                )
            )
            val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return null
                return Gson().fromJson(data, LoginResponse::class.java)
            }
            return null
        } catch (_: Exception) {
            return null
        }
    }

    override fun getMyTeam(token: String): DetailTeamData? {
        try {
            val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
            val olympiadService = client.create(OlympiadService::class.java)
            val call = olympiadService.getMyTeam(
                authorization = "Bearer $token"
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

    override fun getTeamById(id: String): DetailTeamData? {
        try {
            val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
            val olympiadService = client.create(OlympiadService::class.java)
            val call = olympiadService.getTeamById(id = id)
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

    override fun getUserById(id: String): DetailParticipantData? {
        try {
            val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
            val olympiadService = client.create(OlympiadService::class.java)
            val call = olympiadService.getUserById(id = id)
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

    override fun getNotifications(token: String): List<NotificationData> {
        try {
            val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
            val olympiadService = client.create(OlympiadService::class.java)
            val call = olympiadService.getNotifications(
                authorization = "Bearer $token"
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
}