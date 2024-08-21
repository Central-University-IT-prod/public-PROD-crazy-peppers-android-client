package com.knomster.teamx.domain.entities.forParticipants

import com.google.gson.annotations.SerializedName

data class ParticipantData(
    @SerializedName("uid")
    val id: String,
    @SerializedName("full_name")
    val name: String,
    @SerializedName("image_id")
    val photoId: Int,
    val role: String,
    val tags: List<String>,
    val login: String
)