package com.knomster.teamx.domain.entities.forParticipants

import com.google.gson.annotations.SerializedName

data class DetailParticipantData(
    @SerializedName("uid")
    val id: String,
    @SerializedName("full_name")
    val name: String,
    @SerializedName("image_id")
    val photoId: Int,
    val telegram: String,
    val bio: String,
    val role: String,
    val tags: List<String>,
    @SerializedName("is_captain")
    val isCaptain: Boolean
)
