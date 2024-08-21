package com.knomster.teamx.presentation.screens.participant.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.knomster.teamx.R
import com.knomster.teamx.presentation.entities.BottomNavigationBarItem
import com.knomster.teamx.presentation.navigation.admin.BottomNavigationBar
import com.knomster.teamx.presentation.navigation.participant.user.UserNotificationAndDetailNavGraph
import com.knomster.teamx.presentation.navigationData.Screen
import com.knomster.teamx.presentation.navigation.participant.user.UserTeamsAndTeamNavGraph
import com.knomster.teamx.presentation.navigation.participant.user.UserViewNavGraph
import com.knomster.teamx.presentation.navigationData.rememberNavigationState
import com.knomster.teamx.presentation.screens.DetailTeamScreen
import com.knomster.teamx.presentation.screens.participant.MyTeamScreen
import com.knomster.teamx.presentation.screens.participant.captain.CreateTeamScreen
import com.knomster.teamx.presentation.uiElements.LoaderScreen
import com.knomster.teamx.presentation.viewModels.MainAppViewModel

@Composable
fun UserScreen(mainAppViewModel: MainAppViewModel) {
    val hasMyTeam by mainAppViewModel.hasMyTeam.observeAsState()
    val myTeam by mainAppViewModel.myTeam.observeAsState()
    if (hasMyTeam == null) {
        mainAppViewModel.getMyTeam()
        LoaderScreen()
    } else {
        val navigationState = rememberNavigationState()
        val bottomNavigationBarItems = remember {
            if (hasMyTeam == true) {
                listOf(
                    BottomNavigationBarItem(
                        0,
                        "Команды",
                        Screen.UserTeamsAndTeamScreen,
                        R.drawable.ic_teams
                    ),
                    BottomNavigationBarItem(
                        1,
                        "Моя команда",
                        Screen.UserTeamScreen,
                        R.drawable.ic_my_team
                    )
                )
            } else {
                listOf(
                    BottomNavigationBarItem(
                        0,
                        "Команды",
                        Screen.UserTeamsAndTeamScreen,
                        R.drawable.ic_teams
                    ),
                    BottomNavigationBarItem(
                        1,
                        "Уведомления",
                        Screen.UserNotificationsScreen,
                        R.drawable.ic_notifications
                    ),
                )
            }
        }
        val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
        val selectedItemId =
            bottomNavigationBarItems.indexOf(bottomNavigationBarItems.find { item ->
                navBackStackEntry?.destination?.hierarchy?.any {
                    it.route == item.screen.route
                } ?: false
            })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFCFCFC))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                UserViewNavGraph(
                    navHostController = navigationState.navHostController,
                    userNotificationsScreen = {
                        val userNavigationState = rememberNavigationState()
                        UserNotificationAndDetailNavGraph(
                            navHostController = userNavigationState.navHostController,
                            userNotifications = {
                                UserNotificationsScreen(
                                    mainAppViewModel = mainAppViewModel,
                                    goToTeam = { teamId ->
                                        userNavigationState.navigateToUserDetailTeamScreenNotification(teamId)
                                    }
                                )
                            },
                            userDetailTeamScreen = { teamId ->
                                DetailTeamScreen(
                                    mainAppViewModel = mainAppViewModel,
                                    teamId = teamId,
                                    onBack = {
                                        navigationState.navHostController.popBackStack()
                                    },
                                    joinButton = false
                                )
                            }
                        )
                    },
                    userTeamScreen = {
                        MyTeamScreen(
                            mainAppViewModel = mainAppViewModel,
                            teamId = myTeam?.id ?: "",
                            onBack = {
                                navigationState.navHostController.popBackStack()
                            }
                        )
                    },
                    createTeamScreen = {
                        CreateTeamScreen(
                            mainAppViewModel = mainAppViewModel,
                            onBack = {
                                navigationState.navHostController.popBackStack()
                            }
                        )
                    },
                    userTeamsAndTeamScreen = {
                        val userNavigationState = rememberNavigationState()
                        UserTeamsAndTeamNavGraph(
                            navHostController = userNavigationState.navHostController,
                            userTeams = {
                                UserTeamsScreen(
                                    mainAppViewModel = mainAppViewModel,
                                    createTeam = {
                                        navigationState.navigateTo(Screen.CreateTeamScreen.route)
                                    },
                                    goToTeam = { teamId ->
                                        userNavigationState.navigateToUserDetailTeamScreen(teamId)
                                    }
                                )
                            },
                            userDetailTeamScreen = { teamId ->
                                DetailTeamScreen(
                                    mainAppViewModel = mainAppViewModel,
                                    teamId = teamId,
                                    onBack = {
                                        navigationState.navHostController.popBackStack()
                                    }
                                )
                            }
                        )
                    }
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