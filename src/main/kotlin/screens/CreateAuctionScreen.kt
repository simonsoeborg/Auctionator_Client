package screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import factories.LoginItems
import navigation.NavController
import navigation.Screen

@Composable
fun CreateAuctionScreen(navController: NavController) {
    var auctionTitle : String = ""
    var auctionPrice : Int = 0
    var auctionTime : Int = 0 // In seconds / ms

    Column(
        modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(1f).padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.padding(20.dp)) {
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
                value = auctionTitle,
                onValueChange = { auctionTitle = it },
                label = { Text("Enter title") },
                placeholder = { Text("Title") }
            )
        }
        Row(modifier = Modifier.padding(5.dp)) {
            TextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = auctionPrice.toString(),
                onValueChange = { auctionPrice = it.toInt() },
                label = { Text("Enter price in $") },
                placeholder = { Text("Price") }
            )
        }
        Row(modifier = Modifier.padding(5.dp)) {
            TextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = auctionTime.toString(),
                onValueChange = { auctionTime = it.toInt() },
                label = { Text("Enter duration in ms") },
                placeholder = { Text("Duration") }
            )
        }
        Row(modifier = Modifier.padding(5.dp)) {
            Button(onClick = {navController.navigate(Screen.AuctionatorScreen.name)}) {
                Text("Submit")
            }
        }
    }
}
