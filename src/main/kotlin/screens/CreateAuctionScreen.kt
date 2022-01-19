package screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import controller.LobbyController
import factories.LoginItems
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import navigation.NavController
import navigation.Screen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
fun CreateAuctionScreen(navController: NavController, lobbyController: LobbyController) {
    if(LoginItems.isLoggedIn) {
        CreateAuctionComposables(navController, lobbyController)
    } else {
        Column(modifier = Modifier.fillMaxHeight(1f)
            .fillMaxWidth(1f)
            .padding(40.dp),horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Text(text = "You're Not logged In!", fontSize = 30.sp)
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateAuctionComposables(navController: NavController, lobbyController: LobbyController) {
    val auctionTitle = remember { mutableStateOf("") }
    val auctionPrice = remember { mutableStateOf("") }
    val auctionTime = remember { mutableStateOf("") }
    val imageUrl = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxHeight(1f)
            .fillMaxWidth(1f)
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val focusManager = LocalFocusManager.current
        Row(modifier = Modifier.padding(20.dp),) {
            Text("Create new Auction", fontSize = 30.sp)
        }
        Row(modifier = Modifier.padding(5.dp)) {
            TextField(
                value = LoginItems.userName,
                readOnly = true,
                onValueChange = {},
                label = { Text("Username") },
                placeholder = { Text("Username") }
            )
        }
        Row(modifier = Modifier.padding(5.dp)) {
            TextField(
                value = auctionTitle.value,
                onValueChange = { auctionTitle.value = it },
                label = { Text("Enter title") },
                placeholder = { Text("Title") },
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    onPrevious = { focusManager.moveFocus(FocusDirection.Up) }
                ),
                modifier = Modifier.onKeyEvent {
                    when {
                        (it.key == Key.Enter || it.key == Key.Tab) -> {
                            focusManager.moveFocus(FocusDirection.Down)
                            true
                        }
                        (it.isShiftPressed && it.key == Key.Tab) -> {
                            focusManager.moveFocus(FocusDirection.Up)
                            true
                        }
                        else -> false
                    }
                }
            )
        }
        Row(modifier = Modifier.padding(5.dp)) {
            TextField(
                value = auctionPrice.value,
                onValueChange = { auctionPrice.value = it },
                label = { Text("Enter price in $") },
                placeholder = { Text("Price") },
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    onPrevious = { focusManager.moveFocus(FocusDirection.Up) }
                ),
                modifier = Modifier.onKeyEvent {
                    when {
                        (it.key == Key.Enter || it.key == Key.Tab) -> {
                            focusManager.moveFocus(FocusDirection.Down)
                            true
                        }
                        (it.isShiftPressed && it.key == Key.Tab) -> {
                            focusManager.moveFocus(FocusDirection.Up)
                            true
                        }
                        else -> false
                    }
                }
            )
        }
        Row(modifier = Modifier.padding(5.dp)) {
            TextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = auctionTime.value,
                onValueChange = { auctionTime.value = it },
                label = { Text("Enter duration in min") },
                placeholder = { Text("Duration") },
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    onPrevious = { focusManager.moveFocus(FocusDirection.Up) }
                ),
                modifier = Modifier.onKeyEvent {
                    when {
                        (it.key == Key.Enter || it.key == Key.Tab) -> {
                            focusManager.moveFocus(FocusDirection.Down)
                            true
                        }
                        (it.isShiftPressed && it.key == Key.Tab) -> {
                            focusManager.moveFocus(FocusDirection.Up)
                            true
                        }
                        else -> false
                    }
                }
            )
        }
        Row(modifier = Modifier.padding(5.dp)) {
            TextField(
                value = description.value,
                onValueChange = { description.value = it },
                label = { Text("Enter description") },
                placeholder = { Text("Description") },
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    onPrevious = { focusManager.moveFocus(FocusDirection.Up) }
                ),
                modifier = Modifier.onKeyEvent {
                    when {
                        (it.key == Key.Enter || it.key == Key.Tab) -> {
                            focusManager.moveFocus(FocusDirection.Down)
                            true
                        }
                        (it.isShiftPressed && it.key == Key.Tab) -> {
                            focusManager.moveFocus(FocusDirection.Up)
                            true
                        }
                        else -> false
                    }
                }
            )
        }
        Row(modifier = Modifier.padding(5.dp)) {
            TextField(
                value = imageUrl.value,
                onValueChange = { imageUrl.value = it },
                label = { Text("Enter Image URL") },
                placeholder = { Text("Image URL") },
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    onPrevious = { focusManager.moveFocus(FocusDirection.Up) }
                ),
                modifier = Modifier.onKeyEvent {
                    when {
                        (it.key == Key.Enter || it.key == Key.Tab) -> {
                            focusManager.moveFocus(FocusDirection.Down)
                            true
                        }
                        (it.isShiftPressed && it.key == Key.Tab) -> {
                            focusManager.moveFocus(FocusDirection.Up)
                            true
                        }
                        else -> false
                    }
                }
            )
        }
        Row(modifier = Modifier.padding(5.dp)) {
            Button(onClick = {
                runBlocking {
                    launch {
                        lobbyController.createAuction(
                            auctionTitle.value,
                            auctionPrice.value,
                            auctionTime.value,
                            description.value,
                            imageUrl.value
                        )
                    }
                }
                navController.navigate(Screen.AuctionatorScreen.name)
            }
            ) {
                Text("Submit")
            }
        }
    }
}