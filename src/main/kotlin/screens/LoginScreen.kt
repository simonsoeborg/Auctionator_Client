import factories.LoginItems
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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import controller.MainController
import navigation.NavController
import navigation.Screen

@Composable
@Preview
fun LoginScreen (navController: NavController, mainController: MainController) {

    if (LoginItems.isLoggedIn) {
        Column(modifier = Modifier.fillMaxHeight(1f)
            .fillMaxWidth(1f)
            .padding(40.dp),horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Text(text = "You're already logged in!", fontSize = 30.sp)
            }
            Row {
                Text(text = LoginItems.userName, fontSize = 18.sp)
            }
            Row {
                Text(text = "Balance: " + LoginItems.money, fontSize = 18.sp)
            }
        }
    } else {
        LoginComposable(navController, mainController)
    }
}


    private val greetings = listOf(
        "Bonjour",
        "Hola",
        "Ciao"
    )

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginComposable(navController: NavController, mainController: MainController) {
    var userName = remember { mutableStateOf("Human") }
    var userPass = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth(1f).fillMaxHeight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        val focusManager = LocalFocusManager.current
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("${greetings.random()}, ${userName.value}", fontSize = 30.sp)
                Row(modifier = Modifier.padding(10.dp)) {
                    TextField(
                        value = userName.value,
                        onValueChange = { userName.value = it },
                        label = { Text("Enter Username") },
                        placeholder = { Text("Username") },
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
                Row(modifier = Modifier.padding(10.dp)) {
                    TextField(
                        value = userPass.value,
                        onValueChange = { userPass.value = it },
                        label = { Text("Enter Password") },
                        placeholder = { Text("Password") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
                Button(onClick = {
                    LoginItems.userName = userName.value
                    LoginItems.userPass = userPass.value
                    LoginItems.isLoggedIn = true

                    navController.navigate(Screen.AuctionatorScreen.name)
                }) {
                    Text("Lets go!")
                }
            }
        }
    }
}