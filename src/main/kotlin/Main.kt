// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import Model.UserData
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
@Preview
fun app() {
    val userData = MutableStateFlow(UserData("null","DKK",false))

    userData.value=login()

    if(userData.value.isLoggedIn)
        print("It works!")
        auctionator()
}

fun main() = application {
    // runBlocking {  } i stedet for GlobalScope

    Window(onCloseRequest = ::exitApplication,
        title = "Auctionator Client",
        state = rememberWindowState(width = 800.dp, height = 600.dp)
    ) {
        app()
    }
}


