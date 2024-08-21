package com.knomster.teamx.presentation.navigation.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.knomster.teamx.R
import com.knomster.teamx.presentation.entities.BottomNavigationBarItem
import com.knomster.teamx.presentation.navigationData.Screen
import com.knomster.teamx.presentation.navigationData.rememberNavigationState
import com.knomster.teamx.presentation.screens.DetailParticipantScreen
import com.knomster.teamx.presentation.screens.DetailTeamScreen
import com.knomster.teamx.presentation.screens.admin.AdminParticipantsScreen
import com.knomster.teamx.presentation.screens.admin.AdminTeamsScreen
import com.knomster.teamx.presentation.viewModels.MainAppViewModel

@Composable
fun AdminViewScreen(mainAppViewModel: MainAppViewModel) {
    val navigationState = rememberNavigationState()
    val bottomNavigationBarItems = remember {
        listOf(
            BottomNavigationBarItem(0, "Пользователи", Screen.AdminParticipantsScreen, R.drawable.ic_users),
            BottomNavigationBarItem(1, "Команды", Screen.AdminTeamsScreen, R.drawable.ic_teams)
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
            AdminViewNavGraph(
                navHostController = navigationState.navHostController,
                adminTeamsScreen = {
                    AdminTeamsScreen(
                        mainAppViewModel = mainAppViewModel,
                        goToTeam = { teamId ->
                            navigationState.navigateToAdminDetailTeamScreen(teamId)
                        }
                    )
                },
                adminParticipantsScreen = {
                    AdminParticipantsScreen(
                        mainAppViewModel = mainAppViewModel,
                        goToUser = { userId ->
                            navigationState.navigateToAdminDetailUserScreen(userId)
                        }
                    )
                },
                detailTeamScreen = { teamId ->
                    DetailTeamScreen(
                        mainAppViewModel = mainAppViewModel,
                        teamId = teamId,
                        onBack = {
                            navigationState.navHostController.popBackStack()
                        },
                        joinButton = false
                    )
                },
                detailUserScreen = { userId ->
                    DetailParticipantScreen(
                        mainAppViewModel = mainAppViewModel,
                        userId = userId
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

@Composable
fun BottomNavigationBar(
    items: List<BottomNavigationBarItem>,
    selectedItemId: Int,
    onItemSelected: (BottomNavigationBarItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Gray.copy(alpha = 0.1f))
    ) {
        items.forEach { item ->
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        onItemSelected(item)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(30.dp),
                    painter = painterResource(id = item.drawableId),
                    contentDescription = "Bottom navigation item icon",
                    tint = if (item.id == selectedItemId) Color.Black else Color.LightGray
                )
                Text(
                    text = item.title,
                    color = if (item.id == selectedItemId) Color.Black else Color.LightGray
                )
            }
        }
    }
}