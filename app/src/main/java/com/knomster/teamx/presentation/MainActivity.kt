package com.knomster.teamx.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.knomster.teamx.presentation.navigation.MainNavGraph
import com.knomster.teamx.presentation.navigationData.rememberNavigationState
import com.knomster.teamx.presentation.navigation.admin.AdminScreen
import com.knomster.teamx.presentation.screens.login.LoginScreen
import com.knomster.teamx.presentation.navigation.participant.ParticipantScreen
import com.knomster.teamx.presentation.navigationData.Screen
import com.knomster.teamx.presentation.uiElements.LoaderScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainAppViewModel = (application as MainApp).mainAppViewModel
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.Transparent.value.toInt(),
                Color.Transparent.value.toInt()
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.Gray.copy(alpha = 0.1f).value.toInt(),
                Color.Gray.copy(alpha = 0.1f).value.toInt(),
            ),
        )
        setContent {
            val navigationState = rememberNavigationState()
            val token by mainAppViewModel.token.observeAsState()
            if (token == null) {
                mainAppViewModel.getDataFromPreferences()
                LoaderScreen()
            } else {
                Box(
                    modifier = Modifier
                        .navigationBarsPadding()
                ) {
                    MainNavGraph(
                        navHostController = navigationState.navHostController,
                        startDestination =
                        when (mainAppViewModel.isAdmin.value) {
                            true -> {
                                Screen.AdminScreen.route
                            }

                            false -> {
                                Screen.ParticipantScreen.route
                            }

                            null -> {
                                Screen.LoginScreen.route
                            }
                        },
                        loginScreen = {
                            LoginScreen(
                                mainAppViewModel = mainAppViewModel,
                                onLogin = {
                                    mainAppViewModel.isAdmin.value?.let { admin ->
                                        Handler(Looper.getMainLooper()).post {
                                            if (admin) {
                                                navigationState.navigateTo(Screen.AdminScreen.route)
                                            } else {
                                                navigationState.navigateTo(Screen.ParticipantScreen.route)
                                            }
                                        }
                                    }
                                }
                            )
                        },
                        adminScreen = {
                            AdminScreen(mainAppViewModel = mainAppViewModel)
                        },
                        participantScreen = {
                            ParticipantScreen(
                                mainAppViewModel = mainAppViewModel
                            )
                        }
                    )
                }
            }
        }
    }
}