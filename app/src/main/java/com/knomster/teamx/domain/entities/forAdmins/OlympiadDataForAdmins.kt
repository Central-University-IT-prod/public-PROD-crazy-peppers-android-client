package com.knomster.teamx.domain.entities.forAdmins

data class OlympiadDataForAdmins(
    val name: String,
    val deadlineDate: String,
    val participantsForAdmins: OlympiadParticipantsForAdmins
)
