package com.knomster.teamx.presentation.uiElements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knomster.teamx.domain.entities.NotificationData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationItem(
    notificationData: NotificationData,
    onClick: () -> Unit,
    onResponse: (Boolean) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                text = "Приглашение",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = notificationData.name,
                fontSize = 30.sp,
            )
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = notificationData.desc,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )
            {
                Box(
                    modifier = Modifier
                        .width((screenWidth / 3).dp)
                        .height((screenWidth / 9).dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black)
                        .clickable { onResponse(false) }
                        .padding(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width((screenWidth / 3).dp)
                            .height((screenWidth / 9).dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .clickable { onResponse(false) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Отклонить",
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .width((screenWidth/3).dp)
                        .height((screenWidth/9).dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black)
                        .clickable { onResponse(true) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Принять",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun Test() {
    NotificationItem(
        notificationData = NotificationData(
            "",
            "",
            "Crazy Peppers",
            "dwefefeofefefufuwefyuweiyfweyfwyfweyfwyfyweefywefywefyweyfwefywefywefiywifywefwefywyfweyfiweyfwefywify..."
        ),
        onClick = {},
        onResponse = {}
    )
}