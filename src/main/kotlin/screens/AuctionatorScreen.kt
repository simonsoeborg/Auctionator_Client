import factories.LoginItems
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import navigation.NavController

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun AuctionatorScreen(navController: NavController) {

    var onHover = remember { mutableStateOf(false) }

    if(LoginItems.isLoggedIn) {
        currentAuctions(fillAuctionsWithDummyData(),navController)
    } else {
        Text("Not logged in")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun currentAuctions(
    currentAuctions : List<auctionDummyData>,
    navController: NavController
){
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
                    Card(modifier = Modifier.padding(4.dp).height(40.dp).weight(0.6F)) {
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
                }
                LazyVerticalGrid(
                    cells = GridCells.Fixed(1),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(currentAuctions) { auction ->
                        Row(modifier = Modifier.clickable {
                            println(auction.AuctionId)
                            navController.navigate("Auction")
                        }) {
                            Card(modifier = Modifier.padding(4.dp).height(40.dp).weight(0.6F)) {
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
                                    text = auction.AmountOfBidders.toString(),
                                    style = TextStyle(textAlign = TextAlign.Center),
                                )
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
    var auctions : MutableList<auctionDummyData> = mutableListOf<auctionDummyData>()
    auctions.add(auctionDummyData(1,"Norm architecs 'Flip around' for Menu", 6000, getRandomNumber()))
    auctions.add(auctionDummyData(2,"Poul Henningsen PH Sminkebord - mahogni", 30000, getRandomNumber()))
    auctions.add(auctionDummyData(3,"Knapsyet sofa med høj ryg", 4000, getRandomNumber() ))
    auctions.add(auctionDummyData(4,"Deszine Talks. Hyndesæt til 'Folkestolen', Børge Mogensen model J39. Sort læder. (2)", 1000, getRandomNumber() ))
    auctions.add(auctionDummyData(5," Hyndesæt til Børge Mogensens spisestol, model BM1 - sort læder (6)", 2800, getRandomNumber()))
    auctions.add(auctionDummyData(6,"Chronotech, damearmbåndsur, rustfrit stål", 1600, getRandomNumber() ))
    auctions.add(auctionDummyData(7, "Menu. Fire skalstole, model 'Harbour' (4)", 4400, getRandomNumber() ))
    auctions.add(auctionDummyData(8,"Jens Sinding Christensen, havneparti og vinterlandskab, olie på lærred (2)(cd)", 1000, getRandomNumber()))
    auctions.add(auctionDummyData(9,"Grethe Meyer for Menu. Model GM 30. Pendel hvid", 1000, getRandomNumber() ))
    auctions.add(auctionDummyData(10,"Thomas Bernstrand for Muuto. Studio Pendel, Grey.", 1000, getRandomNumber() ))

    return auctions
}

fun getRandomNumber(): Int {
    val rnd = (1..300).random()
    return rnd
}

data class auctionDummyData (
    val AuctionId : Int,
    val AuctionTitle : String,
    val AuctionPrice : Int,
    val AmountOfBidders : Int
)
