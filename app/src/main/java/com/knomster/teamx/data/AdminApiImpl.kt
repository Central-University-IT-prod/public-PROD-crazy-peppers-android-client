package com.knomster.teamx.data

import com.google.gson.Gson
import com.knomster.teamx.domain.Constants
import com.knomster.teamx.domain.entities.OlympiadRequest
import com.knomster.teamx.domain.entities.OlympiadResponse
import com.knomster.teamx.domain.entities.forParticipants.ParticipantData
import com.knomster.teamx.domain.entities.forParticipants.TeamData
import com.knomster.teamx.domain.interfaces.AdminApi
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray

class AdminApiImpl : AdminApi {

    override fun createOlympiad(
        token: String,
        olympiadData: OlympiadRequest
    ): OlympiadResponse? {
        try {
            val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
            val olympiadService = client.create(OlympiadService::class.java)
            val json = Gson().toJson(olympiadData)
            val call = olympiadService.createOlympiad(
                authorization = "Bearer $token",
                body = RequestBody.create(
                    MediaType.parse("application/json"),
                    json
                )
            )
            val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return null
                return Gson().fromJson(data, OlympiadResponse::class.java)
            }
            return null
        } catch (_: Exception) {
            return null
        }
    }

    override fun getOlympiadData(token: String): OlympiadResponse? {
        try {
            val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
            val olympiadService = client.create(OlympiadService::class.java)
            val call = olympiadService.getAdminOlympiad(
                authorization = "Bearer $token"
            )
            val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return null
                return Gson().fromJson(data, OlympiadResponse::class.java)
            }
            return null
        } catch (_: Exception) {
            return null
        }
    }

    override fun createUsers(token: String, count: Long): List<ParticipantData>? {
        try {
            val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
            val olympiadService = client.create(OlympiadService::class.java)
            val call = olympiadService.fillUsers(
                authorization = "Bearer $token",
                count = count
            )
            val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return null
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
            return null
        } catch (_: Exception) {
            return null
        }
    }

    override fun getUsersForAdmin(token: String): List<ParticipantData>? {
        try {
            val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
            val olympiadService = client.create(OlympiadService::class.java)
            val call = olympiadService.getUsersForAdmin(
                authorization = "Bearer $token"
            )
            val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return null
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
            return null
        } catch (_: Exception) {
            return null
        }
    }

    override fun getTeamsForAdmin(): List<TeamData>? {
        val client = OlympiadRetrofitClient.getClient(Constants.apiPrefix)
        val olympiadService = client.create(OlympiadService::class.java)
        val call = olympiadService.getTeamsForAdmin()
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
    }
}