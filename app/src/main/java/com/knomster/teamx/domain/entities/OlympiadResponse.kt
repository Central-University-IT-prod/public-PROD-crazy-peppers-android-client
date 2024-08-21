package com.knomster.teamx.domain.entities

import com.google.gson.annotations.SerializedName

data class OlympiadResponse(
    val name: String,
    @SerializedName("max_participants_in_team")
    val maxParticipants: Long,
    @SerializedName("min_participants_in_team")
    val minParticipants: Long,
    val roles: List<Role>,
    val tags: List<String>,
    val deadline: String,
    @SerializedName("created_at")
    val createdAt: String,
    val oid: String
)

data class Role(
    val name: String,
    @SerializedName("min_in_team")
    val min: Long,
    @SerializedName("max_in_team")
    val max: Long,
)
