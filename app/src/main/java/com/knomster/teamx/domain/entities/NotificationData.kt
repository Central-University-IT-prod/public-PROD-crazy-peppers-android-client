package com.knomster.teamx.domain.entities

import com.google.gson.annotations.SerializedName

data class NotificationData(
    @SerializedName("iid")
    val id: String,
    val from: String,
    val name: String,
    val desc: String
)
