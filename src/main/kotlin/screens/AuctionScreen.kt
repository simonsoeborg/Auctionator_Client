package screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import factories.LoginItems
import kotlinx.coroutines.*
import model.SpecificAuctionData
import navigation.NavController
import java.io.IOException
import java.net.URL

@Composable
fun AuctionScreen(navController: NavController, auctionController: AuctionController){
    val auctionData: State<SpecificAuctionData> = auctionController.currentAuction.collectAsState()
    val onlineBidders: State<Int> = auctionController.onlineBidders.collectAsState()

    Column(
        modifier = Modifier.fillMaxHeight().fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            AmountOfbidders(onlineBidders.value)
        }
        Row{
            LeaveAuctionButton(navController, onLeaveAuction = { auctionController.leaveAuction() })
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
        Row { enterBid(onEnterBid = {auctionController.bidOnAuction(it)}) }
    }
}

@Composable
fun LeaveAuctionButton(navController: NavController, onLeaveAuction: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(onClick = {
            onLeaveAuction()
            navController.navigateBack()
        }) {
            Text("Leave Auction", fontSize = 12.sp)
        }
    }
}

@Composable
fun AmountOfbidders(bidders: Int) {

    Column(
        modifier = Modifier.fillMaxWidth().padding(23.dp),
        horizontalAlignment = Alignment.End
    ){
        Text("Amount of bidders: ${bidders}", fontSize = 20.sp)
    }
}

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
        else {
            Row {
                Text("The current highest bid is ", fontSize = 15.sp)
                Text("$highestBid $", fontSize = 15.sp, color = Color.Green)
            }
        }
    }
}

@Composable
fun enterBid (onEnterBid: (bid: String) -> Unit){
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
                println("Your bid have now been sent: "+ userBid.value)
                onEnterBid(userBid.value.toString())
            }
            else { println("You do not have enough money: "+ userBid.value + " is higher than your current saldo: "+ LoginItems.money)
            }

        }, colors = ButtonDefaults.textButtonColors(backgroundColor = Color(0xFF55aaaa)), modifier = Modifier.padding(start = 20.dp)){
            Text("Send bid", color = Color.White)
        }
    }
}

