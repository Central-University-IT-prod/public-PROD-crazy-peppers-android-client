package com.knomster.teamx.domain.entities.forAdmins

import com.knomster.teamx.domain.entities.forParticipants.ParticipantData

data class ParticipantDataForAdmin(
    val isLogin: Boolean,
    val login: String?,
    val participantData: ParticipantData?
)
