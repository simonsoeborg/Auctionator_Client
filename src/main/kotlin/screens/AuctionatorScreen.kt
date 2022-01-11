import Factories.LoginItems
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.sun.org.apache.xpath.internal.operations.Mod
import navcontroller.NavController
import java.awt.Color
import java.util.*
import kotlin.random.Random.Default.nextInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun AuctionatorScreen(navController: NavController) {

    var onHover = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(1f), horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Column(modifier = Modifier.width(200.dp)) {
                // Auctions
                Text("Hej! " + LoginItems.userName)
                Text("Status: " + LoginItems.isLoggedIn)
                Text("Money: " + LoginItems.money)

            }
            Column(modifier = Modifier.width(600.dp)) {
                LazyVerticalGrid(
                    cells = GridCells.Fixed(1),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(fillAuctionsWithDummyData()) { auction ->
                        Card (modifier = Modifier.padding(4.dp)) {
                            if(onHover.value) {
                                Text(
                                    text = auction.AuctionTitle + " | " + auction.AuctionPrice + " | " + auction.AmountOfBidders,
                                    modifier = Modifier.clickable {
                                        // todo
                                    }.pointerMoveFilter(
                                        onEnter = {
                                            onHover.value = true
                                            false
                                        },
                                        onExit = {
                                            onHover.value = false
                                            false
                                        }
                                    )
                                )
                            }
                            Text(
                                text = auction.AuctionTitle + " | " + auction.AuctionPrice + " | " + auction.AmountOfBidders,
                                modifier = Modifier.clickable {
                                    // todo
                                }.pointerMoveFilter(
                                    onEnter = {
                                        onHover.value = true
                                        false
                                    },
                                    onExit = {
                                        onHover.value = false
                                        false
                                    }
                                )
                            )
                            // text = auction.AuctionTitle, textAlign = TextAlign.Center, style = TextStyle(fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

fun fillAuctionsWithDummyData() : MutableList<auctionDummyData> {
    var auctions : MutableList<auctionDummyData> = mutableListOf<auctionDummyData>()
    auctions.add(auctionDummyData("Norm architecs 'Flip around' for Menu", 6000, getRandomNumber()))
    auctions.add(auctionDummyData("Poul Henningsen PH Sminkebord - mahogni", 30000, getRandomNumber()))
    auctions.add(auctionDummyData("Knapsyet sofa med høj ryg", 4000, getRandomNumber() ))
    auctions.add(auctionDummyData("Deszine Talks. Hyndesæt til 'Folkestolen', Børge Mogensen model J39. Sort læder. (2)", 1000, getRandomNumber() ))
    auctions.add(auctionDummyData(" Hyndesæt til Børge Mogensens spisestol, model BM1 - sort læder (6)", 2800, getRandomNumber()))
    auctions.add(auctionDummyData("Chronotech, damearmbåndsur, rustfrit stål", 1600, getRandomNumber() ))
    auctions.add(auctionDummyData("Menu. Fire skalstole, model 'Harbour' (4)", 4400, getRandomNumber() ))
    auctions.add(auctionDummyData("Jens Sinding Christensen, havneparti og vinterlandskab, olie på lærred (2)(cd)", 1000, getRandomNumber()))
    auctions.add(auctionDummyData("Grethe Meyer for Menu. Model GM 30. Pendel hvid", 1000, getRandomNumber() ))
    auctions.add(auctionDummyData("Thomas Bernstrand for Muuto. Studio Pendel, Grey.", 1000, getRandomNumber() ))

    return auctions
}

fun getRandomNumber(): Int {
    val rnd = (1..300).random()
    return rnd
}

data class auctionDummyData (
    val AuctionTitle : String,
    val AuctionPrice : Int,
    val AmountOfBidders : Int
)