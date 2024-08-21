package com.knomster.teamx.domain.entities.forParticipants

import com.google.gson.annotations.SerializedName

data class TeamData(
    @SerializedName("tid")
    val id: String,
    val name: String,
    @SerializedName("want_members")
    val maxMembers: Long,
    @SerializedName("total_members")
    val currentMembers: Long,
    val tags: List<String>
)
