package com.knomster.teamx.domain.entities

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val token: String,
    @SerializedName("is_admin")
    val isAdmin: Boolean
)
