package com.knomster.teamx.presentation.screens.participant.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knomster.teamx.R
import com.knomster.teamx.domain.entities.forParticipants.TeamData
import com.knomster.teamx.presentation.uiElements.LoaderScreen
import com.knomster.teamx.presentation.viewModels.MainAppViewModel

@Composable
fun UserTeamsScreen(
    mainAppViewModel: MainAppViewModel,
    createTeam: () -> Unit,
    goToTeam: (String) -> Unit
) {
    val hasUserTeams by mainAppViewModel.hasUserTeams.observeAsState()
    val userTeams by mainAppViewModel.userTeams.observeAsState(emptyList())
    if (hasUserTeams == null) {
        mainAppViewModel.getUserTeams()
        LoaderScreen()
    } else {
        Scaffold(
            floatingActionButton = {
                if (mainAppViewModel.hasMyTeam.value == false) {
                    FloatingActionButton(onClick = createTeam) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = "create team"
                        )
                    }
                }
            },
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .shadow(elevation = 2.dp)
                        .background(Color.White)
                        .padding(horizontal = 10.dp)
                        .statusBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Команды",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp
                    )
                }
            },
            containerColor = Color(0xFFFCFCFC)
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(
                    items = userTeams
                ) { teamData ->
                    TeamItem(
                        teamData = teamData,
                        onClick = {
                            goToTeam(teamData.id)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun TeamItem(
    teamData: TeamData,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(7.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = teamData.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "${teamData.currentMembers}/${teamData.maxMembers}")
            }
            FlowRow {
                teamData.tags.forEach { tag ->
                    Card(
                        modifier = Modifier
                            .height(40.dp)
                            .padding(5.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(7.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(horizontal = 5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = tag,
                                style = TextStyle(color = Color.Black),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}