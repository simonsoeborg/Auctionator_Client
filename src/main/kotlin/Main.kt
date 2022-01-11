// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import model.UserData
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import navcontroller.rememberNavController
import screens.NavigationRailSetup
import screens.Screen
import theme.AuctionatorTheme

@ExperimentalMaterialApi
@Composable
@Preview
fun app() {
    val navController by rememberNavController(Screen.LoginScreen.name)

    AuctionatorTheme {
        Surface(
            modifier = Modifier.background(color = MaterialTheme.colors.background)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                NavigationRailSetup(navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
fun main() = application {
    // runBlocking {  } i stedet for GlobalScope
    val icon = painterResource("aLogo.png")

    Tray(
        icon = icon,
        menu = {
            Item("Quit App", onClick = ::exitApplication)
        }
    )

    Window(onCloseRequest = ::exitApplication,
        title = "Auctionator Client",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        icon = icon
    ) {
        app()
    }

}

