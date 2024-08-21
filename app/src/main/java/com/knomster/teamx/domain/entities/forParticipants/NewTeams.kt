package com.knomster.teamx.domain.entities.forParticipants

data class NewTeams(
    val name: String,
    val tags: List<String>,
    val description: String,
    val roles: List<RoleData>
)
