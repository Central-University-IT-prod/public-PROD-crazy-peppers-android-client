package com.knomster.teamx.data

import com.knomster.teamx.data.entities.LoginRequest
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OlympiadService {
    @POST("/auth/login")
    fun signIn(@Body request: LoginRequest): Call<ResponseBody>

    @POST("/olympiads/create")
    fun createOlympiad(
        @Header("Authorization") authorization: String,
        @Body body: RequestBody
    ): Call<ResponseBody>

    @GET("/olympiads/my")
    fun getAdminOlympiad(
        @Header("Authorization") authorization: String,
    ): Call<ResponseBody>

    @POST("/olympiads/fill/{count}")
    fun fillUsers(
        @Header("Authorization") authorization: String,
        @Path("count") count: Long
    ): Call<ResponseBody>

    @GET("/olympiads/users")
    fun getUsersForAdmin(
        @Header("Authorization") authorization: String,
    ): Call<ResponseBody>

    @GET("/users/me")
    fun getMe(
        @Header("Authorization") authorization: String,
    ): Call<ResponseBody>

    @PATCH("/users/me")
    fun changeMe(
        @Header("Authorization") authorization: String,
        @Body body: RequestBody
    ): Call<ResponseBody>

    @GET("/teams")
    fun getTeamsForAdmin(): Call<ResponseBody>

    @GET("/teams/matched")
    fun getTeamsForUser(
        @Header("Authorization") authorization: String,
    ): Call<ResponseBody>

    @GET("/teams/my")
    fun getMyTeam(
        @Header("Authorization") authorization: String,
    ): Call<ResponseBody>

    @POST("/teams")
    fun createTeam(
        @Header("Authorization") authorization: String,
        @Body body: RequestBody
    ): Call<ResponseBody>

    @GET("/teams/{id}")
    fun getTeamById(
        @Path("id") id: String
    ): Call<ResponseBody>

    @GET("/users/{id}")
    fun getUserById(
        @Path("id") id: String
    ): Call<ResponseBody>

    @POST("/invites/as-user/{id}")
    fun joinToTeam(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    ): Call<ResponseBody>

    @POST("/invites/as-captain/{id}")
    fun inviteToTeam(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    ): Call<ResponseBody>

    @GET("/invites/received")
    fun getNotifications(
        @Header("Authorization") authorization: String
    ): Call<ResponseBody>

    @PATCH("/invites/process/to-user/{id}")
    fun responseNotificationUser(
        @Path("id") id: String,
        @Query("action") action: Int
    ): Call<ResponseBody>

    @PATCH("/invites/process/to-team/{id}")
    fun responseNotificationCaptain(
        @Path("id") id: String,
        @Query("action") action: Int
    ): Call<ResponseBody>

    @GET("/teams/users-matched")
    fun getUsersForCaptain(
        @Header("Authorization") authorization: String
    ): Call<ResponseBody>
}