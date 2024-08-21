package com.knomster.teamx.domain.entities

data class RoleInfo(
    val name: String,
    val minParticipants: Long,
    val maxParticipants: Long,
)
