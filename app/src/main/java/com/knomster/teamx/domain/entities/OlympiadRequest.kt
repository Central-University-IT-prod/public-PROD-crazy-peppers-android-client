package com.knomster.teamx.domain.entities

data class OlympiadRequest(
    val name: String,
    val max_participants_in_team: Long,
    val min_participants_in_team: Long,
    val roles: List<RoleRequst>,
    val tags: List<String>,
    val deadline: String
)

data class RoleRequst(
    val name: String,
    val max_in_team: Long,
    val min_in_team: Long
)