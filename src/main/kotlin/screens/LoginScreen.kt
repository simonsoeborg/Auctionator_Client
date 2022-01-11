import Factories.LoginItems
import model.UserData
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navcontroller.NavController
import sun.rmi.runtime.Log

@Composable
@Preview
fun LoginScreen (navController: NavController) : UserData {
    val userData = remember { mutableStateOf( UserData("null","DKK",false) )}
    var userName = remember { mutableStateOf("") }
    var userPass = remember { mutableStateOf("") }


    Column(modifier = Modifier.fillMaxWidth(1f).fillMaxHeight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Login form")
                Row(modifier = Modifier.padding(10.dp)) {
                    TextField(
                        value = userName.value,
                        onValueChange = { userName.value = it },
                        label = { Text("Enter Username") },
                        placeholder = { Text("Username") }
                    )
                }/*
                Row(modifier = Modifier.padding(10.dp)) {
                    TextField(
                        value = userPass.value,
                        onValueChange = { userPass.value = it },
                        label = { Text("Enter Password") },
                        placeholder = { Text("Password") }
                    )
                }*/
                Button(onClick = {
                    LoginItems.userName = userName.value
                    LoginItems.userPass = userPass.value
                    LoginItems.isLoggedIn = true

                    navController.navigate(Screen.AuctionatorScreen.name)
                }) {
                    Text("Submit")
                }
            }
        }
    }

    return userData.value
}
