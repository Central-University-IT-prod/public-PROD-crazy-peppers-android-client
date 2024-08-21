package com.knomster.teamx.presentation.screens.admin

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.knomster.teamx.R
import com.knomster.teamx.domain.entities.Participants
import com.knomster.teamx.domain.entities.RoleInfo
import com.knomster.teamx.presentation.DatePickerDialog
import com.knomster.teamx.presentation.viewModels.CreateOlympiadScreenViewModel
import com.knomster.teamx.presentation.viewModels.CreateOlympiadViewModelFactory
import com.knomster.teamx.presentation.viewModels.MainAppViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale

@Composable
fun CreateOlympiadScreen(
    mainAppViewModel: MainAppViewModel,
    onBack: () -> Unit
) {
    val viewModel: CreateOlympiadScreenViewModel = viewModel(
        factory = CreateOlympiadViewModelFactory(mainAppViewModel)
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
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
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        containerColor = Color(0xFFFCFCFC)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                FirstBlock(viewModel = viewModel)
            }
            item {
                ParticipantsInTeam(viewModel = viewModel)
            }
            item {
                Roles(viewModel = viewModel)
            }
            item {
                Tags(viewModel = viewModel)
            }
            item {
                CreateButton(
                    viewModel = viewModel,
                    onError = { error ->
                        scope.launch {
                            snackbarHostState.showSnackbar(error)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun FirstBlock(viewModel: CreateOlympiadScreenViewModel) {
    val olympiadName by viewModel.olympiadName.observeAsState("")
    val deadline by viewModel.deadline.observeAsState(System.currentTimeMillis() + 1000 * 60 * 60 * 24)
    val participantsCount by viewModel.participantsCount.observeAsState(2)

    var showDatePickerDialog by remember { mutableStateOf(false) }
    if (showDatePickerDialog) {
        DatePickerDialog(
            time = deadline,
            onCloseDialog = { showDatePickerDialog = false },
            onPick = { year, month, day ->
                val newDate =
                    LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(System.currentTimeMillis()),
                        ZoneId.systemDefault()
                    ).atZone(
                        ZoneId.systemDefault()
                    ).withYear(year).withMonth(month).withDayOfMonth(day)
                viewModel.changeDeadline(newDate.toInstant().toEpochMilli())
            }
        )
    }
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
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                BasicTextField(
                    value = olympiadName,
                    onValueChange = viewModel::changeOlympiadName,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    textStyle = TextStyle.Default.copy(fontSize = 24.sp)
                )
                if (olympiadName.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.olympiad_name),
                        style = TextStyle(color = Color.Gray),
                        modifier = Modifier.padding(start = 4.dp),
                        fontSize = 24.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Column(
                modifier = Modifier.padding(start = 5.dp)
            ) {
                Row {
                    Text(
                        text = stringResource(id = R.string.number_of_participants) + ":",
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    LongTextField(
                        number = participantsCount,
                        onChange = viewModel::changeParticipantsCount
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .clickable { showDatePickerDialog = true }
                ) {
                    val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                    Text(
                        text = stringResource(id = R.string.deadline) + ":",
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = dateFormatter.format(deadline),
                        fontStyle = FontStyle.Italic,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun Roles(viewModel: CreateOlympiadScreenViewModel) {
    val participants by viewModel.participants.observeAsState(Participants(2L, 5L, emptyList()))
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
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.roles),
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            participants.roles.forEach { role ->
                RoleItem(
                    roleInfo = role,
                    onChange = {
                        viewModel.changeRole(oldRole = role, newRole = it)
                    },
                    onDelete = { viewModel.deleteRole(role) }
                )
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                onClick = {
                    viewModel.addRole()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add role",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun RoleItem(
    roleInfo: RoleInfo,
    onChange: (RoleInfo) -> Unit,
    onDelete: () -> Unit
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
            Row {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    BasicTextField(
                        value = roleInfo.name,
                        onValueChange = { onChange(roleInfo.copy(name = it)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = TextStyle.Default.copy(fontSize = 24.sp)
                    )
                    if (roleInfo.name.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.role_name),
                            style = TextStyle(color = Color.Gray),
                            modifier = Modifier.padding(start = 4.dp),
                            fontSize = 24.sp
                        )
                    }
                }
                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete role")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.min),
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    LongTextField(
                        number = roleInfo.minParticipants,
                        onChange = {
                            onChange(roleInfo.copy(minParticipants = it))
                        }
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = stringResource(id = R.string.max),
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    LongTextField(
                        number = roleInfo.maxParticipants,
                        textAlign = TextAlign.End,
                        onChange = {
                            onChange(roleInfo.copy(maxParticipants = it))
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun Tags(viewModel: CreateOlympiadScreenViewModel) {
    val tags by viewModel.tags.observeAsState(emptyList())

    var showChangeTahDialog by rememberSaveable { mutableStateOf<String?>(null) }
    showChangeTahDialog?.let { tag ->
        ChangeTagDialog(
            tag = tag,
            onSave = { viewModel.changeTag(oldTag = tag, newTag = it) },
            onDismissRequest = { showChangeTahDialog = null },
            onDelete = {
                viewModel.deleteTag(tag)
            }
        )
    }
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
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.tags),
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(5.dp))

            FlowRow {
                tags.forEach { tag ->
                    TagItem(
                        tag = tag,
                        onClick = { showChangeTahDialog = tag }
                    )
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
                    onClick = { viewModel.addTag() }
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TagItem(
    tag: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(40.dp)
            .padding(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(7.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
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

@Composable
private fun LongTextField(
    number: Long,
    textAlign: TextAlign = TextAlign.Start,
    onChange: (Long) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            modifier = Modifier.wrapContentWidth(),
            value = number.toString(),
            onValueChange = { newValue ->
                if (newValue.isEmpty()) {
                    onChange(0)
                } else {
                    if (newValue.all { it.isDigit() } && newValue.length < 10) {
                        if (number == 0L) {
                            onChange(newValue.trim('0').toLongOrNull() ?: 0L)
                        } else {
                            onChange(newValue.toLongOrNull() ?: number)
                        }
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            textStyle = TextStyle(
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = textAlign
            )
        )
    }
}

@Composable
private fun ChangeTagDialog(
    tag: String,
    onSave: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onDelete: () -> Unit
) {
    val screenWidthDp =
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) LocalConfiguration.current.screenWidthDp else LocalConfiguration.current.screenHeightDp
    var newTag by rememberSaveable { mutableStateOf(tag) }
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .width(screenWidthDp.dp - 20.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    BasicTextField(
                        value = newTag,
                        onValueChange = { newTag = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = TextStyle.Default.copy(fontSize = 18.sp)
                    )
                    if (tag.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.olympiad_name),
                            style = TextStyle(color = Color.Gray),
                            modifier = Modifier.padding(start = 4.dp),
                            fontSize = 18.sp
                        )
                    }
                }
                IconButton(
                    onClick = {
                        onDelete()
                        onDismissRequest()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete tag"
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    onClick = {
                        onSave(newTag)
                        onDismissRequest()
                    }
                ) {
                    Text(text = stringResource(id = R.string.save))
                }
            }
        }
    }
}

@Composable
private fun ParticipantsInTeam(viewModel: CreateOlympiadScreenViewModel) {
    val participants by viewModel.participants.observeAsState(Participants(2L, 5L, emptyList()))
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
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.number_of_participants_in_team),
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.min),
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    LongTextField(
                        number = participants.min,
                        onChange = {
                            viewModel.setParticipants(min = it)
                        }
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = stringResource(id = R.string.max),
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    LongTextField(
                        number = participants.max,
                        textAlign = TextAlign.End,
                        onChange = {
                            viewModel.setParticipants(max = it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CreateButton(
    viewModel: CreateOlympiadScreenViewModel,
    onError: (String) -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            ),
            onClick = {
                val result = viewModel.checkValues(context)
                if (result.result) {
                    viewModel.createOlympiad()
                } else onError(result.error)
            }
        ) {
            Text(text = stringResource(id = R.string.create))
        }
    }
}