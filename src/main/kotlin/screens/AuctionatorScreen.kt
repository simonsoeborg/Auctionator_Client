import factories.LoginItems
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import navigation.NavController
import navigation.Screen
import screens.auctionatorViewModel


var auctions : MutableList<auctionDummyData> = mutableListOf<auctionDummyData>()

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun AuctionatorScreen(navController: NavController) {

    if(auctions.size < 1) {
        fillAuctionsWithDummyData()
    }
    if(LoginItems.isLoggedIn) {
        currentAuctions(navController)
    } else {
        Column(
            modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Not logged in")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun currentAuctions(navController: NavController){

    Column(
        modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Column(modifier = Modifier.width(200.dp)) {
                Card(modifier = Modifier.padding(4.dp).height(80.dp)) {
                    Column {
                        Row {
                            Text("Hej " + LoginItems.userName + "!")
                        }
                        Row {
                            Text("Logged In: " + LoginItems.isLoggedIn)
                        }
                        Row {
                            Text("Balance: " + LoginItems.money + " $")
                        }
                    }
                }

            }
            Column(modifier = Modifier.width(600.dp)) {
                Row {
                    Card(modifier = Modifier.padding(4.dp).height(40.dp).weight(0.5F)) {
                        Text(
                            text = "Title",
                            style = TextStyle(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        )
                    }
                    Card(modifier = Modifier.padding(4.dp).height(40.dp).weight(0.2F)) {
                        Text(
                            text = "Price",
                            style = TextStyle(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        )
                    }
                    Card(modifier = Modifier.padding(4.dp).height(40.dp).weight(0.2F)) {
                        Text(
                            text = "Bidders",
                            style = TextStyle(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        )
                    }
                    Card(modifier = Modifier.padding(4.dp).height(40.dp).weight(0.1F)) {
                    }
                }
                LazyVerticalGrid(
                    cells = GridCells.Fixed(1),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(auctions) { auction ->
                        Row(modifier = Modifier.clickable {
                            println(auction.AuctionId)
                            navController.navigate(Screen.AuctionScreen.name)
                        }) {
                            Card(modifier = Modifier.padding(4.dp).height(40.dp).weight(0.5F)) {
                                Text(
                                    text = auction.AuctionTitle,
                                )
                            }
                            Card(modifier = Modifier.padding(4.dp).height(40.dp).weight(0.2F)) {
                                Text(
                                    text = auction.AuctionPrice.toString() + " $",
                                    style = TextStyle(textAlign = TextAlign.Center),
                                )
                            }
                            Card(modifier = Modifier.padding(4.dp).height(40.dp).weight(0.2F)) {
                                Text(
                                    text = auction.TimeRemaining.toString(),
                                    style = TextStyle(textAlign = TextAlign.Center),
                                )
                            }
                            Card(modifier = Modifier.padding(4.dp).height(40.dp).weight(0.1F)) {
                                Button( onClick = {
                                    removeItemFromAuctions(auction.AuctionId)
                                } ) {
                                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
                                }
                            }

                        }
                        // text = auction.AuctionTitle, textAlign = TextAlign.Center, style = TextStyle(fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

fun fillAuctionsWithDummyData() : MutableList<auctionDummyData> {
    auctions.add(auctionDummyData(1,"Norm architecs 'Flip around' for Menu", 6000, getRandomNumber()))
    auctions.add(auctionDummyData(2,"Poul Henningsen PH Sminkebord - mahogni", 30000, getRandomNumber()))
    auctions.add(auctionDummyData(3,"Knapsyet sofa med høj ryg", 4000, getRandomNumber()))
    auctions.add(auctionDummyData(4,"Deszine Talks. Hyndesæt til 'Folkestolen', Børge Mogensen model J39. Sort læder. (2)", 1000, getRandomNumber() ))
    auctions.add(auctionDummyData(5," Hyndesæt til Børge Mogensens spisestol, model BM1 - sort læder (6)", 2800, getRandomNumber()))
    auctions.add(auctionDummyData(6,"Chronotech, damearmbåndsur, rustfrit stål", 1600, getRandomNumber()))

    return auctions
}

fun removeItemFromAuctions(itemId : Int) {
    auctions.forEach {
        if(it.AuctionId == itemId) {
            auctions.remove(it)
        }
    }
}

fun addItemToAuctions(item : auctionDummyData) {
    auctions.add(item)
}

fun getRandomNumber(): Int {
    val rnd = (1..300).random()
    return rnd
}

data class auctionDummyData (
    val AuctionId : Int,
    val AuctionTitle : String,
    val AuctionPrice : Int,
    val TimeRemaining : Int
)
