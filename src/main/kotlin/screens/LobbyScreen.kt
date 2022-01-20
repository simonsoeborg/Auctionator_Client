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
import androidx.compose.ui.unit.sp
import controller.AuctionController
import controller.LobbyController
import factories._AuctionID
import kotlinx.coroutines.*
import model.AuctionData
import navigation.NavController
import navigation.Screen


@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun LobbyScreen(navController: NavController, lobbyController: LobbyController, auctionController: AuctionController) {
    lobbyController.refreshAllAuctions()
    val allAuctions : State<List<AuctionData>> = lobbyController.allAuctions.collectAsState()

    if(LoginItems.isLoggedIn) {
        currentAuctions(navController, allAuctions.value, auctionController)
    } else {
        Column(modifier = Modifier.fillMaxHeight(1f)
            .fillMaxWidth(1f)
            .padding(40.dp),horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Text(text = "You're Not logged In!", fontSize = 30.sp)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun currentAuctions(navController: NavController, auctionsState: List<AuctionData>, auctionController: AuctionController){
    val scope = CoroutineScope(Dispatchers.IO)
    Column(
        modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Column(modifier = Modifier.width(400.dp).padding(30.dp)) {
                Box(modifier = Modifier.padding(4.dp).height(150.dp)) {
                    Column {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Hi " + LoginItems.userName + "!",
                                style = TextStyle(textAlign = TextAlign.Center, fontSize = 24.sp)
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Welcome to Auctionator. Feel free to try and bid on auctions.\nIf you want to leave an ongoing auction press the Leave Auction button.",
                                style = TextStyle( fontSize = 12.sp)
                            )
                        }
                    }
                }

            }
            Column(modifier = Modifier.width(600.dp).padding(30.dp)) {
                Row(modifier = Modifier.padding(8.dp)) {
                    Card(modifier = Modifier.padding(4.dp).height(40.dp).weight(0.5F)) {
                        Text(
                            text = "Title",
                            style = TextStyle(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        )
                    }
                    Card(modifier = Modifier.padding(4.dp).height(40.dp).weight(0.2F)) {
                        Text(
                            text = "Current price",
                            style = TextStyle(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        )
                    }
                    Card(modifier = Modifier.padding(4.dp).height(40.dp).weight(0.2F)) {
                        Text(
                            text = "Auction ends",
                            style = TextStyle(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        )
                    }
                }
                LazyVerticalGrid(
                    cells = GridCells.Fixed(1),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(auctionsState) { auction ->
                        Row(modifier = Modifier.clickable {

                            auctionController.auctionID = auction.auctionId.toString()
                            auctionController.joinAuction()
                            MainScope().launch {
                                delay(300L)
                            }

                            scope.launch {
                                delay(200L)
                                //auctionController.userOnline()
                                auctionController.listenForNewAuctionData()
                            }
                            navController.navigate(Screen.AuctionScreen.name)
                        }) {
                            Card(modifier = Modifier.padding(4.dp).height(40.dp).weight(0.5F)) {
                                Text(
                                    text = auction.auctionTitle,
                                    style = TextStyle(textAlign = TextAlign.Center)
                                )
                            }
                            Card(modifier = Modifier.padding(4.dp).height(40.dp).weight(0.2F)) {
                                Text(
                                    text = if (auction.highestBid.equals("0")){
                                                  auction.auctionPrice
                                            } else{
                                              auction.highestBid
                                              },
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
