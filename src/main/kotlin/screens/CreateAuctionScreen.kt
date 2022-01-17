package screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import controller.MainController
import factories.LoginItems
import navigation.NavController
import navigation.Screen

@Composable
@Preview
fun CreateAuctionScreen(navController: NavController, mainController: MainController) {
    var auctionTitle = remember { mutableStateOf("") }
    var auctionURL = remember { mutableStateOf("") }
    var auctionPrice = remember { mutableStateOf(0) }
    var auctionTime = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(1f).padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.padding(20.dp), ) {
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
                placeholder = { Text("Title") }
            )
        }
        Row(modifier = Modifier.padding(5.dp)) {
            TextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = auctionPrice.value.toString(),
                onValueChange = { auctionPrice.value = it.toInt() },
                label = { Text("Enter price in $") },
                placeholder = { Text("Price") }
            )
        }
        Row(modifier = Modifier.padding(5.dp)) {
            TextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = auctionTime.value.toString(),
                onValueChange = { auctionTime.value = it.toInt() },
                label = { Text("Enter duration in ms") },
                placeholder = { Text("Duration") }
            )
        }
        Row(modifier = Modifier.padding(5.dp)) {
            TextField(
                value = auctionURL.value,
                onValueChange = { auctionURL.value = it },
                label = { Text("Enter Image URL") },
                placeholder = { Text("Image URL") }
            )
        }
        Row(modifier = Modifier.padding(5.dp)) {
            Button(onClick = {
                // addItemToAuctions(auctionDummyData(auctions.size+1, auctionTitle.value, auctionPrice.value, auctionTime.value))
                navController.navigate(Screen.AuctionatorScreen.name)
            }
            ) {
                Text("Submit")
            }
        }
    }
}
