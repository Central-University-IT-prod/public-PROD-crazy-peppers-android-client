package com.knomster.teamx.presentation.screens.participant.captain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knomster.teamx.presentation.uiElements.LoaderScreen
import com.knomster.teamx.presentation.uiElements.NotificationItem
import com.knomster.teamx.presentation.viewModels.MainAppViewModel

@Composable
fun CaptainNotificationsScreen(
    mainAppViewModel: MainAppViewModel,
    goToTeam: (String) -> Unit
) {
    val hasNotifications by mainAppViewModel.hasNotifications.observeAsState()
    val notifications by mainAppViewModel.notifications.observeAsState(emptyList())
    if (hasNotifications == null) {
        mainAppViewModel.getNotifications()
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
                    text = "Уведомления",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = notifications
                ) { notificationData ->
                    NotificationItem(
                        notificationData = notificationData,
                        onClick = { goToTeam(notificationData.from) },
                        onResponse = { action ->
                            mainAppViewModel.responseNotificationCaptain(notificationData.id, action)
                        }
                    )
                }
            }
        }
    }
}