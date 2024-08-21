package com.knomster.teamx.domain.entities.forParticipants

import com.knomster.teamx.domain.entities.Participants

data class OlympiadData(
    val name: String,
    val tags: List<String>,
    val deadlineDate: String,
    val participants: Participants
)
