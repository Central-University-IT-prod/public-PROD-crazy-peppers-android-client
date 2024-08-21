package com.knomster.teamx.presentation.entities

import com.knomster.teamx.presentation.navigationData.Screen
import java.io.Serializable

data class BottomNavigationBarItem(
    val id: Int,
    val title: String,
    val screen: Screen,
    val drawableId: Int,
): Serializable
