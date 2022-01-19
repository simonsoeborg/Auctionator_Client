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
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import controller.AuctionController
import controller.LobbyController
import factories.LoginItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import model.SpecificAuctionData
import navigation.NavController
import java.io.IOException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun AuctionScreen(navController: NavController, lobbyController: LobbyController){
    val auctionController = AuctionController()
    val auctionData : State<SpecificAuctionData> = auctionController.currentAuction.collectAsState()

    Column(
            modifier = Modifier.fillMaxHeight().fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                AmountOfbidders()
            }
            Row {
                GetItemImage(auctionData.value.auctionImageURL)
            }
            Row {
                getAuctionTitle(auctionData.value.auctionTitle)
            }
            Row {
                getAuctionDescription(auctionData.value.auctionDescription)
            }
            Row {
                currentBidAndPrice(auctionData.value.auctionHighestBid, auctionData.value.auctionPrice)
            }
            Row { enterBid(auctionController) }
        }
}

@Composable
fun AmountOfbidders() {
    // Todo - Server needs to keep track of this?
    val Bidders = remember { mutableStateOf(0) }
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
        Row(modifier = Modifier.padding(15.dp)) {
            Text(auctionTitle, fontSize = 30.sp)
        }
    }
}

@Composable
fun getAuctionDescription(description: String) {
    Column(
        modifier = Modifier.fillMaxWidth(.25f).padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.padding(bottom = 10.dp)) {
            Text(description, fontSize = 12.sp)
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

        if (highestBid.toInt()==0){
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
fun enterBid (auctionController: AuctionController){
    val userBid = remember { mutableStateOf(0) }
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
                runBlocking {
                    launch {
                        println("Your bid have now been sent: "+ userBid.value)
                        auctionController.bidOnAuction(userBid.value.toString())
                    }
                }
            }
            else { println("You do not have enough money: "+ userBid.value + " is higher than your current saldo: "+ LoginItems.money)
            }

        }, colors = ButtonDefaults.textButtonColors(backgroundColor = Color(0xFF55aaaa)), modifier = Modifier.padding(start = 20.dp)){
            Text("Send bid", color = Color.White)
        }
    }
}

@Composable
fun countDownClock (auctionController: AuctionController) {

    val endTime = auctionController.currentAuction.value.auctionTimeRemaining; // Auction timeout time
    val initialDate = Calendar.getInstance() // Current DateTime
    initialDate.timeZone = TimeZone.getTimeZone("GMT+1") // Set TimeZone

    // Format date so it matches the pattern from auctionTimeRemaining:
    val formatter = SimpleDateFormat("HH:mm:ss")
    val currentTimeStamp = formatter.format(initialDate.time)

    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(
            text = currentTimeStamp,
            color = Color.White
        )
    }
}
