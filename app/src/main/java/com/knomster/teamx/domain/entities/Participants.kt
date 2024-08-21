package com.knomster.teamx.domain.entities

data class Participants(
    val max: Long,
    val min: Long,
    val roles: List<RoleInfo>
)
