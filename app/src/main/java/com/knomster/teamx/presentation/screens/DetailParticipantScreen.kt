package com.knomster.teamx.presentation.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.knomster.teamx.R
import com.knomster.teamx.domain.Constants
import com.knomster.teamx.presentation.uiElements.LoaderScreen
import com.knomster.teamx.presentation.viewModels.DetailParticipantViewModel
import com.knomster.teamx.presentation.viewModels.DetailParticipantViewModelFactory
import com.knomster.teamx.presentation.viewModels.MainAppViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailParticipantScreen(
    mainAppViewModel: MainAppViewModel,
    userId: String,
    inviteButton: Boolean = true
) {
    val viewModel: DetailParticipantViewModel = viewModel(
        factory = DetailParticipantViewModelFactory(mainAppViewModel)
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val meInformation by mainAppViewModel.meInformation.observeAsState()
    val hasUserData by viewModel.hasUserData.observeAsState()
    val user by viewModel.user.observeAsState()
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val context = LocalContext.current
    if (hasUserData == null) {
        viewModel.getUserData(userId)
        mainAppViewModel.getMeInformation()
        LoaderScreen()
    } else {
        val scope = rememberCoroutineScope()
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            containerColor = Color(0xFFFCFCFC)
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .padding(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size((screenWidth / 2).dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                                .padding(2.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .padding(4.dp),
                            ) {
                                Image(
                                    modifier = Modifier
                                        .size((screenWidth / 2).dp)
                                        .clip(CircleShape),
                                    painter = painterResource(
                                        id =  Constants.photos[meInformation?.photoId ?: 0] ?: R.drawable.cat_1
                                    ),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "User image",
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            shape = RoundedCornerShape(7.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(20.dp),
                                    text = user?.name ?: "",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    text = user?.bio ?: "",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(10.dp),
                                text = "Роль: ${user?.role}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        if (user?.tags.isNullOrEmpty().not()) {
                            Tags(tags = user?.tags ?: emptyList())
                        }
                        if (user?.telegram.isNullOrEmpty().not()) {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(1.dp),
                                onClick = {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://t.me/${user?.telegram}")
                                    )
                                    context.startActivity(intent)
                                },
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_telegram),
                                        contentDescription = "telegram"
                                    )
                                    Text(
                                        text = "Telegram: @${user?.telegram}",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        if (meInformation?.isCaptain == true && inviteButton) {
                            Spacer(modifier = Modifier.height(5.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(paddingValues)
                                    .padding(10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Black
                                    ),
                                    onClick = {
                                        mainAppViewModel.inviteToTeam(userId) {
                                            scope.launch {
                                                snackbarHostState.showSnackbar(if (it) "Запрос отправлен" else "Вы уже отправили запрос")
                                            }
                                        }
                                    }
                                ) {
                                    Text(text = "Пригласить")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Tags(tags: List<String>) {
    Text(
        text = "Теги:",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )
    FlowRow {
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