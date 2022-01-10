import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.awt.Color
import javax.swing.border.Border

@Composable
@Preview
fun Login(): Boolean {

    var userName = remember { mutableStateOf("") }
    var userPass = remember { mutableStateOf("") }
    var isLoggedIn = remember { mutableStateOf(false)}

    DesktopMaterialTheme {
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
                    Button(onClick = { isLoggedIn.value = true }) {
                        Text("Submit")
                    }
                }
            }
        }
    }

    return isLoggedIn.value
}