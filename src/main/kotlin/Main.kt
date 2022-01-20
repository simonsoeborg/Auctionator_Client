// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import controller.AuctionController
import controller.LobbyController
import navigation.NavigationRailSetup
import navigation.Screen
import navigation.rememberNavController
import theme.AuctionatorTheme


@OptIn(ExperimentalMaterialApi::class)
fun main() = application {
    val icon = painterResource("aLogo.png")

    Tray(
        icon = icon,
        menu = {
            Item("Quit App", onClick = ::exitApplication)
        }
    )

    Window(onCloseRequest = ::exitApplication,
        title = "Auctionator Client",
        state = rememberWindowState(width = 1400.dp, height = 1000.dp, placement = WindowPlacement.Maximized),
        icon = icon
    ) {
        app()
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun app() {
    val navController by rememberNavController(Screen.LoginScreen.name)
    val lobbyController = LobbyController()
    val auctionController = AuctionController()
    AuctionatorTheme {
        Surface(
            modifier = Modifier.background(color = MaterialTheme.colors.background)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                NavigationRailSetup(navController, lobbyController, auctionController)
            }
        }
    }
}

