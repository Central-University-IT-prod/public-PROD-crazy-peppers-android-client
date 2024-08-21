package com.knomster.teamx.domain.entities

data class TeamForApi(
    val name: String,
    val description: String,
    val roles: List<RoleWhenCreate>,
    val tags: List<String>
)
