package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.loadImageBitmap
import controller.MainController
import factories.LoginItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import navigation.NavController
import java.io.IOException
import java.net.URL


@Composable
fun AuctionScreen(navController: NavController, mainController: MainController){

        Column(
            modifier = Modifier.fillMaxHeight().fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                AmountOfbidders()
            }
            Row {
                GetItemImage(mainController.currentAuction.value.auctionImageURL)
            }
            Row {
                getAuctionTitle(mainController.currentAuction.value.auctionTitle)
            }
            Row {
                currentBidAndPrice(mainController.currentAuction.value.auctionHighestBid, mainController.currentAuction.value.auctionPrice)
            }
            Row { enterBid() }
        }
}

@Composable
fun AmountOfbidders() {
    // Todo - Server needs to keep track of this?
    var Bidders = remember { mutableStateOf(0) }
    Column(
        modifier = Modifier.fillMaxWidth().padding(3.dp),
        horizontalAlignment = Alignment.End
    ){
        Row(modifier = Modifier.padding(20.dp)) {
            Text("Amount of bidders: ${Bidders.value}", fontSize = 20.sp)
        }
    }
}

//------------------------------------------hente et billede på nettet--------------------------------------------------

@Composable
fun GetItemImage(url: String) {
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            load = { loadImageBitmap(url) },
            painterFor = { remember { BitmapPainter(it) } },
            contentDescription = "Sample",
            modifier = Modifier.width(200.dp)
        )
    }
}

@Composable
fun <T> AsyncImage(
    load: suspend () -> T,
    painterFor: @Composable (T) -> Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val image: T? by produceState<T?>(null) {
        value = withContext(Dispatchers.IO) {
            try {
                load()
            } catch (e: IOException) {
                // instead of printing to console, you can also write this to log,
                // or show some error placeholder
                e.printStackTrace()
                null
            }
        }
    }

    if (image != null) {
        Image(
            painter = painterFor(image!!),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}

fun loadImageBitmap(url: String): ImageBitmap =
    URL(url).openStream().buffered().use(::loadImageBitmap)


//----------------------------------------------------------------------------------------------------------------------

@Composable
fun getAuctionTitle(auctionTitle: String) {
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.padding(20.dp)) {
            Text(auctionTitle, fontSize = 30.sp)
        }
    }
}

@Composable
fun currentBidAndPrice(highestBid: String, minimumPrice : String) {
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text("The starting price is ", fontSize = 15.sp, modifier = Modifier.padding(bottom = 25.dp))
            Text("$minimumPrice $", color = Color(0xFF55aaaa), modifier = Modifier.padding(bottom = 25.dp))
        }
        if (highestBid.toInt()==1){
            Row {
                Text("There is currently")
                Text(" NO ", color = Color.Red)
                Text("bids on this item")
            }
        }
        else
            Row { Text("The current highest bid is ", fontSize = 15.sp)
            Text("$highestBid $", fontSize = 15.sp, color = Color.Green)
            }
    }
}

@Composable
fun enterBid (){
    var userBid = remember { mutableStateOf(0) }
    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = userBid.value.toString(),
            onValueChange = { userBid.value = it.toInt()},
            label = { Text("Enter bid") },
            placeholder = { Text("bid") },
        )

        TextButton(onClick = {
            if (userBid.value <= LoginItems.money){
                // Send bid
                println("you bid have now been send: you bidded "+ userBid.value)
            }
            else { println("You do not have enough money: "+ userBid.value + " is higher than your current saldo: "+ LoginItems.money)
            }

        }, colors = ButtonDefaults.textButtonColors(backgroundColor = Color(0xFF55aaaa)), modifier = Modifier.padding(start = 20.dp)){
            Text("Send bid", color = Color.White)
        }
    }
}