package com.knomster.teamx.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.knomster.teamx.R
import com.knomster.teamx.domain.Constants
import com.knomster.teamx.domain.entities.forParticipants.ParticipantData
import com.knomster.teamx.domain.entities.forParticipants.RoleData
import com.knomster.teamx.presentation.uiElements.LoaderScreen
import com.knomster.teamx.presentation.viewModels.DetailTeamScreenViewModel
import com.knomster.teamx.presentation.viewModels.DetailTeamScreenViewModelFactory
import com.knomster.teamx.presentation.viewModels.MainAppViewModel
import kotlinx.coroutines.launch

@Composable
fun DetailTeamScreen(
    mainAppViewModel: MainAppViewModel,
    teamId: String,
    joinButton: Boolean = true,
    onBack: () -> Unit
) {
    val viewModel: DetailTeamScreenViewModel = viewModel(
        factory = DetailTeamScreenViewModelFactory(mainAppViewModel)
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val myTeam = mainAppViewModel.myTeam.observeAsState()
    val hasTeamData by viewModel.hasTeamData.observeAsState()
    val team by viewModel.team.observeAsState()
    if (hasTeamData == null) {
        viewModel.getTeamData(teamId)
        mainAppViewModel.getMyTeam()
        LoaderScreen()
    } else {
        if (hasTeamData == true) {
            val scope = rememberCoroutineScope()
            Scaffold(
                containerColor = Color(0xFFFCFCFC),
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
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
                            text = "TeamX",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 24.sp
                        )
                    }
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    NameAndDescription(
                        name = team?.name ?: "",
                        description = team?.description ?: ""
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Roles(roles = team?.roles ?: emptyList())
                    Spacer(modifier = Modifier.height(5.dp))
                    Tags(tags = team?.tags ?: emptyList())
                    if (myTeam.value == null && joinButton) {
                        Spacer(modifier = Modifier.height(5.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black
                                ),
                                onClick = {
                                    mainAppViewModel.joinToTeam(teamId) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(if (it) "Запрос отправлен" else "Вы уже отправили запрос")
                                        }
                                    }
                                }
                            ) {
                                Text(text = "Хочу сюда!")
                            }
                        }
                    }
                }
            }
        } else {
            onBack()
        }
    }
}

@Composable
private fun NameAndDescription(
    name: String,
    description: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            modifier = Modifier
                .padding(start = 5.dp),
            text = name,
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
        Divider(
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        if (description.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                text = description,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun Roles(roles: List<RoleData>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Состав команды",
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.height(10.dp))
        roles.forEach { roleData ->
            RoleItem(roleData = roleData)
        }
    }
}

@Composable
fun RoleItem(
    roleData: RoleData
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(7.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            if (roleData.participant == null) {
                Text(
                    text = roleData.name,
                    color = Color.LightGray,
                    fontSize = 18.sp
                )
            } else {
                UserItem(participantData = roleData.participant)
            }
        }
    }
}

@Composable
private fun UserItem(participantData: ParticipantData) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape),
            painter = painterResource(
                id = Constants.photos[participantData.photoId] ?: R.drawable.cat_1
            ),
            contentDescription = "Participant image",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = participantData.name,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = participantData.role,
            fontSize = 16.sp
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Tags(tags: List<String>) {
    FlowRow(
        modifier = Modifier.padding(horizontal = 15.dp)
    ) {
        tags.forEach { tag ->
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
                        text = "#$tag",
                        style = TextStyle(color = Color.Black),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}