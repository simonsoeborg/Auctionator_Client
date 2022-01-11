import Factories.LoginItems
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navcontroller.NavController

@Composable
@Preview
fun AuctionatorScreen(navController: NavController) {


    Column(modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(1f), horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Column(modifier = Modifier.width(200.dp)) {
                // Auctions
                Text(navController.currentScreen.value)
                Button(
                    onClick = {
                        navController.navigate(Screen.LoginScreen.name)
                    }) {
                }

            }
            Column(modifier = Modifier.width(200.dp)) {
                //
                Text("Hej! " + LoginItems.userName)
                Text("Status: " + LoginItems.isLoggedIn)
                Text("Money: " + LoginItems.money)
            }
        }
    }
}

