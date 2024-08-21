package com.knomster.teamx.presentation.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knomster.teamx.R
import com.knomster.teamx.presentation.viewModels.MainAppViewModel
import kotlinx.coroutines.launch
import java.lang.Integer.min

@Composable
fun LoginScreen(
    mainAppViewModel: MainAppViewModel,
    onLogin: () -> Unit
) {
    var login by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCFCFC)),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        containerColor = Color(0xFFFCFCFC)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.fillMaxHeight(0.13f))
                TeamXLogo()
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = stringResource(id = R.string.login_hint),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = login,
                    onValueChange = { login = it },
                    label = { Text(text = stringResource(id = R.string.login)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        cursorColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = stringResource(id = R.string.password)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        cursorColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    onClick = {
                        mainAppViewModel.signIn(login, password) { result ->
                            if (result) {
                                onLogin()
                            } else {
                                scope.launch {
                                    snackbarHostState.showSnackbar(context.resources.getString(R.string.login_error))
                                }
                            }
                        }
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.signIn),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun TeamXLogo() {
    val screenWidth = min(LocalConfiguration.current.screenWidthDp, LocalConfiguration.current.screenHeightDp)
    Box(
        modifier = Modifier
            .width((screenWidth / 5 * 3).dp)
            .height((screenWidth / 3.1).dp)
            .background(Color.White)
            .drawWithContent {
                drawContent()
                drawRoundedTopLeftCorner(screenWidth / 17f)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontWeight = FontWeight.ExtraBold,
            fontSize = (screenWidth / 10).sp,
        )
    }
}

private fun DrawScope.drawRoundedTopLeftCorner(
    borderWidth: Float
) {
    val cornerRadius = 50f

    drawPath(
        path = Path().apply {
            moveTo(0f, cornerRadius + size.height / 2)
            lineTo(0f, cornerRadius)
            arcTo(
                rect = Rect(0f, 0f, cornerRadius * 2, cornerRadius * 2),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(cornerRadius + size.width * 0.8f - cornerRadius, 0f)

            moveTo(size.width, size.height / 2 - cornerRadius)
            lineTo(size.width, size.height - cornerRadius)
            arcTo(
                rect = Rect(size.width - cornerRadius * 2, size.height - cornerRadius * 2, size.width, size.height),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(cornerRadius + size.width * 0.2f - cornerRadius, size.height)
        },
        color = Color.Black,
        style = Stroke(width = borderWidth)
    )
}