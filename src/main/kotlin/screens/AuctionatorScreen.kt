import factories.LoginItems
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import controller.MainController
import model.AuctionData
import navigation.NavController
import navigation.Screen


@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun AuctionatorScreen(navController: NavController, mainController: MainController) {
    val allAuctions : State<List<AuctionData>> = mainController.allAuctions.collectAsState()
    val currentAuction : State<AuctionData> = mainController.currentAuction.collectAsState()

    if(LoginItems.isLoggedIn) {
        currentAuctions(navController, allAuctions, currentAuction)
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
fun currentAuctions(navController: NavController, auctionsState: State<List<AuctionData>>, current : State<AuctionData>){

    val auctions : List<AuctionData> = auctionsState.value
    var temp : MutableList<AuctionData> = emptyList<AuctionData>().toMutableList()
    temp.add(current.value)

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
                            text = "Time Remaining",
                            style = TextStyle(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        )
                    }
                }
                LazyVerticalGrid(
                    cells = GridCells.Fixed(1),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(temp) { auction ->
                        Row(modifier = Modifier.clickable {
                            navController.navigate(Screen.AuctionScreen.name)
                        }) {
                            Card(modifier = Modifier.padding(4.dp).height(40.dp).weight(0.5F)) {
                                Text(
                                    text = auction.auctionTitle,
                                )
                            }
                            Card(modifier = Modifier.padding(4.dp).height(40.dp).weight(0.2F)) {
                                Text(
                                    text = auction.auctionPrice + " $",
                                    style = TextStyle(textAlign = TextAlign.Center),
                                )
                            }
                            Card(modifier = Modifier.padding(4.dp).height(40.dp).weight(0.2F)) {
                                Text(
                                    text = auction.auctionEndTime,
                                    style = TextStyle(textAlign = TextAlign.Center),
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}
