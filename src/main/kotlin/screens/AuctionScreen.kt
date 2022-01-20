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
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
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
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun AuctionScreen(navController: NavController, auctionController: AuctionController){
    val auctionData: State<SpecificAuctionData> = auctionController.currentAuction.collectAsState()
    val onlineBidders: State<Int> = auctionController.onlineBidders.collectAsState()
    val timeRemaining: State<Int> = auctionController.timeRemaining.collectAsState()

    Column(
        modifier = Modifier.fillMaxHeight().fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row{
            Column(
            modifier = Modifier.fillMaxWidth().padding(50.dp),
            horizontalAlignment = Alignment.End
            ){
                AmountOfbidders(onlineBidders.value)
                Timer(timeRemaining.value, onLeaveAuction = { auctionController.leaveAuction()}, navController)
                LeaveAuctionButton(navController, onLeaveAuction = { auctionController.leaveAuction() } )
            }
        }
        Row { ItemImage(auctionData.value.auctionImageURL) }
        Row { AuctionTitle(auctionData.value.auctionTitle, auctionData.value.userName)}
        Row { AuctionDescription(auctionData.value.auctionDescription) }
        Row { StartingPrice(auctionData.value.auctionPrice) }
        Row { CurrentBid(auctionData.value.auctionHighestBid) }
        Row { EnterBid(onEnterBid = {auctionController.bidOnAuction(it)}) }
    }
}

@Composable
fun LeaveAuctionButton(navController: NavController, onLeaveAuction: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,)
    {
        Button(modifier = Modifier.padding(top = 10.dp, end = 10.dp), onClick = {
            onLeaveAuction()
            navController.navigateBack()
        }) {
            Text("Leave Auction")
        }
    }
}

@Composable
fun AmountOfbidders(bidders: Int) {

        Text("Amount of bidders: ${bidders}", fontSize = 20.sp)
}

//------------------------------------------hente et billede på nettet--------------------------------------------------

@Composable
fun ItemImage(url: String) {
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
fun AuctionTitle(auctionTitle: String, seller : String) {
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
                Text(auctionTitle, fontSize = 30.sp)
                Text(seller, fontSize = 15.sp, modifier = Modifier.padding(top = 15.dp))
            }
    }



@Composable
fun AuctionDescription(description: String) {
    Column(
        modifier = Modifier.fillMaxWidth(.25f).padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.padding(bottom = 10.dp)) {
            Text(description, fontSize = 14.sp)
        }
    }
}

@Composable
fun StartingPrice(startingPrice: String){
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text("The starting price is ", fontSize = 14.sp, modifier = Modifier.padding(bottom = 25.dp))
            Text("$startingPrice", fontSize = 14.sp, color = Color(0xFF55aaaa), modifier = Modifier.padding(bottom = 25.dp))
        }
    }
}

@Composable
fun CurrentBid(highestBid: String) {
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (highestBid.toInt()==0){
            Row {
                Text("There are currently")
                Text(" NO ", color = Color.Red)
                Text("bids on this item")
            }
        }
        else {
            Row {
                Text("The current highest bid is ", fontSize = 14.sp)
                Text("$highestBid $", fontSize = 14.sp, color = Color.Green)
            }
        }
    }
}

@Composable
fun EnterBid (onEnterBid: (bid: String) -> Unit) {
    val userBid = remember { mutableStateOf(0) }
    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = userBid.value.toString(),
            onValueChange = { userBid.value = it.toInt() },
            label = { Text("Enter bid") },
            placeholder = { Text("bid") },
        )

        Button(onClick = {
            if (userBid.value <= LoginItems.money) {
                // Send bid
                println("Your bid have now been sent: " + userBid.value)
                onEnterBid(userBid.value.toString())
            } else {
                println("You do not have enough money: " + userBid.value + " is higher than your current saldo: " + LoginItems.money)
            }

        }, modifier = Modifier.padding(start = 20.dp)) {
            Text("Send bid", color = Color.White)
        }
    }
}

@Composable
fun Timer(
    totalTime: Int, onLeaveAuction: () -> Unit, navController : NavController) {
    var currentTime by remember {
        mutableStateOf(totalTime)
    }
    var isTimerRunning by remember {
        mutableStateOf(true)
    }

    if (currentTime == 0){

        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            delay(5000L)
            onLeaveAuction()
            navController.navigateBack()
            }
        }

    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if(currentTime > 0 && isTimerRunning) {
            delay(1000)
            currentTime -= 1

        }
    }
    Box(
        contentAlignment = Alignment.Center,

    ) {
       Text(
            text = if(currentTime!=0){("Time remaining: "+ (currentTime/60).toString()+ " min "+(currentTime%60).toString()+" sec")}
           else{
               "Auction is over!"
               },
            fontSize = 22.sp,
            fontWeight = Bold,
            color = Color.White
        )
    }
}

