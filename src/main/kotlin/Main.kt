// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import Model.UserData
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
@Preview
fun App() {
    // val userData =
    var text by remember { mutableStateOf("Hello, World!") }
    var isLoggedIn by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("null") }

    isLoggedIn = Login()

    if(isLoggedIn)
        Auctionator()

}

fun main() = application {
    // val userData = MutableStateFlow(UserData)

    Window(onCloseRequest = ::exitApplication,
        title = "Auctionator Client",
        state = rememberWindowState(width = 800.dp, height = 600.dp)
    ) {
        App()
    }
}


