package com.knomster.teamx.presentation.screens.admin

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.knomster.teamx.R
import com.knomster.teamx.domain.Constants
import com.knomster.teamx.domain.entities.forParticipants.ParticipantData
import com.knomster.teamx.presentation.uiElements.LoaderScreen
import com.knomster.teamx.presentation.viewModels.MainAppViewModel

@Composable
fun AdminParticipantsScreen(
    mainAppViewModel: MainAppViewModel,
    goToUser: (String) -> Unit
) {
    val hasUsersForAdmin by mainAppViewModel.hasUsersForAdmin.observeAsState()
    if (hasUsersForAdmin == null) {
        mainAppViewModel.getUsersForAdmin()
        LoaderScreen()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFCFCFC))
        ) {
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
                    text = "Все пользователи",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                )
            }
            LazyColumn {
                items(
                    items = mainAppViewModel.usersForAdmin.value ?: emptyList()
                ) { participantData ->
                    UserItem(
                        participantData = participantData,
                        onClick = {
                            goToUser(participantData.id)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun UserItem(
    participantData: ParticipantData,
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
            if (participantData.name.isEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = participantData.login
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = Constants.photos[participantData.photoId] ?: R.drawable.cat_1),
                        contentDescription = "Participant image",
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = participantData.name)
                }
                FlowRow {
                    participantData.tags.forEach { tag ->
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
}