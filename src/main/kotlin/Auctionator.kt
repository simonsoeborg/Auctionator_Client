import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun auctionator() {



    DesktopMaterialTheme {
        Column(modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Column(modifier = Modifier.width(200.dp)) {
                    // Auctions

                }
                Column(modifier = Modifier.width(200.dp)) {
                    // 
                }
            }
        }
    }
}