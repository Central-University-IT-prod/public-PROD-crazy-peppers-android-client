package com.knomster.teamx.domain.entities.forAdmins

data class CreateOlympiadResult(
    val resultCode: Int,
    val olympiadData: OlympiadDataForAdmins?,
    val errorText: String?
)
