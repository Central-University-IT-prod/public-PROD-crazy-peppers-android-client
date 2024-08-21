package com.knomster.teamx.presentation.navigation.participant.captain

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.knomster.teamx.R
import com.knomster.teamx.presentation.entities.BottomNavigationBarItem
import com.knomster.teamx.presentation.navigationData.Screen
import com.knomster.teamx.presentation.navigation.admin.BottomNavigationBar
import com.knomster.teamx.presentation.navigationData.rememberNavigationState
import com.knomster.teamx.presentation.screens.DetailParticipantScreen
import com.knomster.teamx.presentation.screens.participant.captain.CaptainNotificationsScreen
import com.knomster.teamx.presentation.screens.participant.MyTeamScreen
import com.knomster.teamx.presentation.screens.participant.captain.CaptainUsersScreen
import com.knomster.teamx.presentation.uiElements.LoaderScreen
import com.knomster.teamx.presentation.viewModels.MainAppViewModel

@Composable
fun CaptainScreen(
    mainAppViewModel: MainAppViewModel
) {
    val hasMyTeam by mainAppViewModel.hasMyTeam.observeAsState()
    val myTeam by mainAppViewModel.myTeam.observeAsState()
    if (hasMyTeam == null) {
        mainAppViewModel.getMyTeam()
        LoaderScreen()
    } else {
        val navigationState = rememberNavigationState()
        val bottomNavigationBarItems = remember {
            listOf(
                BottomNavigationBarItem(
                    0,
                    "Пользователи",
                    Screen.CaptainUsersAndUserScreen,
                    R.drawable.ic_users
                ),
                BottomNavigationBarItem(
                    1,
                    "Моя команда",
                    Screen.CaptainTeamScreen,
                    R.drawable.ic_my_team
                ),
                BottomNavigationBarItem(
                    2,
                    "Уведомления",
                    Screen.CaptainNotificationsScreen,
                    R.drawable.ic_notifications
                )
            )
        }
        val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
        val selectedItemId =
            bottomNavigationBarItems.indexOf(bottomNavigationBarItems.find { item ->
                navBackStackEntry?.destination?.hierarchy?.any {
                    it.route == item.screen.route
                } ?: false
            })
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                CaptainViewNavGraph(
                    navHostController = navigationState.navHostController,
                    captainNotificationsScreen = {
                        val captainNavigationState = rememberNavigationState()
                        CaptainNotificationAndDetailUserNavGraph(
                            navHostController = captainNavigationState.navHostController,
                            captainNotifications = {
                                CaptainNotificationsScreen(
                                    mainAppViewModel = mainAppViewModel,
                                    goToTeam = { userId ->
                                        captainNavigationState.navigateToCaptainDetailUserScreen(
                                            userId
                                        )
                                    }
                                )
                            },
                            captainDetailUserScreen = { userId ->
                                DetailParticipantScreen(
                                    mainAppViewModel = mainAppViewModel,
                                    userId = userId,
                                    inviteButton = false
                                )
                            }
                        )
                    },
                    captainTeamScreen = {
                        MyTeamScreen(
                            mainAppViewModel = mainAppViewModel,
                            teamId = myTeam?.id ?: "",
                            onBack = {
                                navigationState.navHostController.popBackStack()
                            }
                        )
                    },
                    captainUsersAndUserScreen = {
                        val captainNavigationState = rememberNavigationState()
                        CaptainTeamsAndTeamNavGraph(
                            navHostController = captainNavigationState.navHostController,
                            captainUsers = {
                                CaptainUsersScreen(
                                    mainAppViewModel = mainAppViewModel,
                                    goToTeam = { teamId ->
                                        captainNavigationState.navigateToCaptainDetailUserScreenNotification(
                                            teamId
                                        )
                                    }
                                )
                            },
                            captainDetailUserScreen = { userId ->
                                DetailParticipantScreen(
                                    mainAppViewModel = mainAppViewModel,
                                    userId = userId
                                )
                            }
                        )
                    },
                )
            }
            BottomNavigationBar(
                items = bottomNavigationBarItems,
                selectedItemId = selectedItemId,
                onItemSelected = { item ->
                    mainAppViewModel.updateAllData()
                    if (item.id != selectedItemId) {
                        navigationState.navigateTo(item.screen.route)
                    }
                }
            )
        }
    }
}