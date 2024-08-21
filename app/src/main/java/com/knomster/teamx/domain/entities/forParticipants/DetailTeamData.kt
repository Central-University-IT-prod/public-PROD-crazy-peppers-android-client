package com.knomster.teamx.domain.entities.forParticipants

import com.google.gson.annotations.SerializedName

data class DetailTeamData(
    @SerializedName("tid")
    val id: String,
    val name: String,
    val description: String,
    val roles: List<RoleData>,
    val tags: List<String>
)