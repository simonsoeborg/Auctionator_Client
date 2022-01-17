package screens

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.Density
import controller.MainController
import factories.LoginItems.userName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import navigation.NavController
import java.io.File
import java.io.IOException
import java.net.URL


@Composable
fun AuctionScreen(navController: NavController, mainController: MainController){
    DesktopMaterialTheme {
        Column(
            modifier = Modifier.fillMaxHeight().fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                ConnectingBidders()
            }
            Row {
                GetItemImage()
            }
            Row {
                GetBidName()
            }
            Row {
                MakingABid()
            }
        }
    }
}

@Composable
fun ConnectingBidders() {
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
fun GetItemImage() {
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            load = { loadImageBitmap("https://lw-cdn.com/images/0F993425AC1B/k_45ca6b8e23245aeca9cf07f29b954e73;w_1600;h_1600;q_100/elaine-led-vaeglampe-med-en-lampe-messing.jpg") },
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
fun GetBidName() {
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.padding(20.dp)) {
            Text("item Name*", fontSize = 30.sp)
        }
    }
}
@Composable
fun MakingABid() {
    var userBid = remember { mutableStateOf(0) }
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.padding(5.dp)) {
            Text("higest bidder is " + userName + " with the current Bid: " + userBid.value.toString(), fontSize = 15.sp)
        }

        Row(modifier = Modifier.padding(10.dp)) {
            TextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = userBid.value.toString(),
                onValueChange = { userBid.value = it.toInt()},
                label = { Text("Enter bid") },
                placeholder = { Text("bid") }
            )
        }
    }
}