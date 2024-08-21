package com.knomster.teamx.presentation.screens.participant

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.knomster.teamx.R
import com.knomster.teamx.domain.Constants
import com.knomster.teamx.domain.entities.forParticipants.DetailParticipantData
import com.knomster.teamx.presentation.getScreenWidthDp
import com.knomster.teamx.presentation.uiElements.LoaderScreen
import com.knomster.teamx.presentation.viewModels.MainAppViewModel

@Composable
fun RegisterScreen(mainAppViewModel: MainAppViewModel) {
    val hasMeInformation by mainAppViewModel.hasMeInformation.observeAsState()
    if (hasMeInformation == null) {
        mainAppViewModel.getMeInformation()
        LoaderScreen()
    } else {
        val meInformation by mainAppViewModel.meInformation.observeAsState()
        val olympiad by mainAppViewModel.olympiad.observeAsState()
        val screenWidth = LocalConfiguration.current.screenWidthDp

        var name by rememberSaveable { mutableStateOf(meInformation?.name ?: "") }
        var bio by rememberSaveable { mutableStateOf(meInformation?.bio ?: "") }
        var telegram by rememberSaveable { mutableStateOf(meInformation?.telegram ?: "") }

        var roleExpanded by rememberSaveable { mutableStateOf(false) }
        var role by rememberSaveable { mutableStateOf(meInformation?.role ?: "") }

        var userTags by rememberSaveable { mutableStateOf(meInformation?.tags ?: emptyList()) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFCFCFC))
                .statusBarsPadding()
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
                                    id = Constants.photos[meInformation?.photoId ?: 0] ?: R.drawable.cat_1
                                ),
                                contentScale = ContentScale.Crop,
                                contentDescription = "User image",
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = {
                            Text(text = "ФИО")
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            cursorColor = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = bio,
                        onValueChange = { bio = it },
                        label = {
                            Text(text = "Описание")
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            cursorColor = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = telegram,
                        onValueChange = { telegram = it },
                        leadingIcon = {
                            Text(
                                text = "@",
                                color = Color.Black,
                                fontSize = 16.sp
                            )
                        },
                        label = {
                            Text(text = "Ваш telegram")
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            cursorColor = Color.Black
                        )
                    )
                    DropdownMenuItem(
                        text = { Text(text = role.ifEmpty { "Выберете роль" }) },
                        onClick = { roleExpanded = true })
                    DropdownMenu(
                        expanded = roleExpanded,
                        onDismissRequest = { roleExpanded = false }
                    ) {
                        olympiad?.participants?.roles?.forEach { roleInfo ->
                            if (roleInfo.name != role) {
                                DropdownMenuItem(
                                    text = { Text(text = roleInfo.name) },
                                    onClick = {
                                        role = roleInfo.name
                                        roleExpanded = false
                                    }
                                )
                            }
                        }
                    }
                    Tags(
                        allTags = olympiad?.tags ?: emptyList(),
                        userTags = userTags,
                        onChange = {
                            userTags = it
                        }
                    )
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black
                        ),
                        onClick = {
                            mainAppViewModel.changeMe(
                                DetailParticipantData(
                                    id = meInformation?.id ?: "",
                                    name = name,
                                    photoId = meInformation?.photoId ?: 0,
                                    telegram = telegram,
                                    bio = bio,
                                    role = role,
                                    tags = userTags,
                                    isCaptain = mainAppViewModel.meInformation.value?.isCaptain ?: false
                                )
                            )
                        }
                    ) {
                        Text(text = "Сохранить")
                    }
                }
            }
        }
    }
}

@OptIn(
    ExperimentalLayoutApi::class, ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
private fun Tags(
    allTags: List<String>,
    userTags: List<String>,
    onChange: (List<String>) -> Unit
) {
    var showAddTagDialog by rememberSaveable { mutableStateOf(false) }
    if (showAddTagDialog) {
        AddTagDialog(
            tags = allTags.toMutableList().apply {
                removeAll { it in userTags }
            },
            onPick = {
                val newTags = userTags.toMutableList()
                newTags.add(it)
                onChange(newTags)
            },
            onDismissRequest = { showAddTagDialog = false }
        )
    }
    FlowRow {
        userTags.forEach { tag ->
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .shadow(elevation = 4.dp)
                    .background(Color.White)
                    .combinedClickable(
                        onClick = {},
                        onLongClick = {
                            val newTags = userTags.toMutableList()
                            newTags.remove(tag)
                            onChange(newTags)
                        }
                    )
                    .padding(5.dp),
                contentAlignment = Alignment.Center
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
    Card(
        modifier = Modifier
            .height(40.dp)
            .padding(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(7.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = {
            showAddTagDialog = true
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color.Black)
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add tag",
                tint = Color.White
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun AddTagDialog(
    tags: List<String>,
    onPick: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    val screenWidthDp = getScreenWidthDp()
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .width(screenWidthDp.dp - 20.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .verticalScroll(scrollState)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        onClick = {
                            onPick(tag)
                            onDismissRequest()
                        }
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