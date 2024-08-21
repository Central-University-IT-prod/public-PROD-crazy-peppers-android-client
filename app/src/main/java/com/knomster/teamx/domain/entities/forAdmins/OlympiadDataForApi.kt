package com.knomster.teamx.domain.entities.forAdmins

import com.knomster.teamx.domain.entities.Participants

data class OlympiadDataForApi(
    val name: String,
    val participants: Participants,
    val deadlineDate: Long
)
