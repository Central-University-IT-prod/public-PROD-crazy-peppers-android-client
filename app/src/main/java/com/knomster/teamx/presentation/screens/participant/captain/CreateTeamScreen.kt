package com.knomster.teamx.presentation.screens.participant.captain

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.knomster.teamx.R
import com.knomster.teamx.domain.entities.RoleWhenCreate
import com.knomster.teamx.presentation.getScreenWidthDp
import com.knomster.teamx.presentation.viewModels.CreateTeamScreenViewModel
import com.knomster.teamx.presentation.viewModels.CreateTeamScreenViewModelFactory
import com.knomster.teamx.presentation.viewModels.MainAppViewModel
import kotlinx.coroutines.launch

@Composable
fun CreateTeamScreen(
    mainAppViewModel: MainAppViewModel,
    onBack: () -> Unit
) {
    val viewModel: CreateTeamScreenViewModel = viewModel(
        factory = CreateTeamScreenViewModelFactory(mainAppViewModel)
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val olympiad by mainAppViewModel.olympiad.observeAsState()
    val teamTags by viewModel.tags.observeAsState(emptyList())
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
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    NameAndDescription(viewModel = viewModel)
                }
                item {
                    Roles(viewModel = viewModel)
                }
                item {
                    Tags(
                        allTags = olympiad?.tags ?: emptyList(),
                        teamTags = teamTags,
                        onChange = { viewModel.changeTags(it) }
                    )
                }
                item {
                    CreateButton(
                        viewModel = viewModel,
                        onError = { error ->
                            scope.launch {
                                snackbarHostState.showSnackbar(error)
                            }
                        },
                        onBack = onBack
                    )
                }
            }
        }
    }
}

@Composable
private fun NameAndDescription(viewModel: CreateTeamScreenViewModel) {
    val name by viewModel.name.observeAsState("")
    val description by viewModel.description.observeAsState("")
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
                    value = name,
                    onValueChange = viewModel::changeName,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    textStyle = TextStyle.Default.copy(fontSize = 24.sp)
                )
                if (name.isEmpty()) {
                    Text(
                        text = "Название команды",
                        style = TextStyle(color = Color.Gray),
                        modifier = Modifier.padding(start = 4.dp),
                        fontSize = 24.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                BasicTextField(
                    value = description,
                    onValueChange = viewModel::changeDescription,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    textStyle = TextStyle.Default.copy(fontSize = 18.sp)
                )
                if (description.isEmpty()) {
                    Text(
                        text = "Описание команды",
                        style = TextStyle(color = Color.Gray),
                        modifier = Modifier.padding(start = 4.dp),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun Roles(viewModel: CreateTeamScreenViewModel) {
    val roles by viewModel.roles.observeAsState()
    if (viewModel.roles.value == null) {
        viewModel.setDefaultRoles()
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
                text = stringResource(id = R.string.roles),
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "от ${viewModel.mainAppViewModel.olympiad.value?.participants?.min ?: 2L} до ${viewModel.mainAppViewModel.olympiad.value?.participants?.max ?: 5L}"
            )
            Spacer(modifier = Modifier.height(5.dp))
            roles?.forEach { role ->
                val olympiadRole =
                    viewModel.mainAppViewModel.olympiad.value?.participants?.roles?.find { it.name == role.name }
                RoleItem(
                    roleWhenCreate = role,
                    descText = "от ${olympiadRole?.minParticipants ?: 0L} до ${olympiadRole?.maxParticipants ?: 2L}",
                    onChange = {
                        viewModel.changeRole(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun RoleItem(
    roleWhenCreate: RoleWhenCreate,
    descText: String,
    onChange: (RoleWhenCreate) -> Unit
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = roleWhenCreate.name,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = descText,
                    fontSize = 18.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        if (roleWhenCreate.available - 1 >= 0) {
                            onChange(roleWhenCreate.copy(available = roleWhenCreate.available - 1))
                        }
                    }) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.ic_minus),
                        contentDescription = "minus"
                    )
                }
                Text(
                    text = roleWhenCreate.available.toString(),
                    fontSize = 30.sp
                )
                IconButton(onClick = {
                    onChange(roleWhenCreate.copy(available = roleWhenCreate.available + 1))
                }) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "minus"
                    )
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
    teamTags: List<String>,
    onChange: (List<String>) -> Unit
) {
    var showAddTagDialog by rememberSaveable { mutableStateOf(false) }
    if (showAddTagDialog) {
        AddTagDialog(
            tags = allTags.toMutableList().apply {
                removeAll { it in teamTags }
            },
            onPick = {
                val newTags = teamTags.toMutableList()
                newTags.add(it)
                onChange(newTags)
            },
            onDismissRequest = { showAddTagDialog = false }
        )
    }
    FlowRow {
        teamTags.forEach { tag ->
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .shadow(elevation = 4.dp)
                    .background(Color.White)
                    .combinedClickable(
                        onClick = {},
                        onLongClick = {
                            val newTags = teamTags.toMutableList()
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

@Composable
private fun CreateButton(
    viewModel: CreateTeamScreenViewModel,
    onError: (String) -> Unit,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            ),
            onClick = {
                val result = viewModel.checkValues()
                if (result.result) {
                    viewModel.createTeam {
                        Handler(Looper.getMainLooper()).post(onBack)
                    }
                } else onError(result.error)
            }
        ) {
            Text(text = stringResource(id = R.string.create))
        }
    }
}