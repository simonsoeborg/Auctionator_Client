package screens

import AuctionatorScreen
import LoginScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import navcontroller.NavController
import navcontroller.NavigationHost
import navcontroller.composable


enum class Screen(
    val label: String,
    val icon: ImageVector
) {
    AuctionatorScreen(
        label = "Auctionator",
        icon = Icons.Filled.Home
    ),
    LoginScreen(
        label = "Login",
        icon = Icons.Filled.Lock
    ),
    CreateAuctionScreen(
        label = "Create",
        icon = Icons.Filled.Add
    )
}


@Composable
fun CustomNavigationHost(
    navController: NavController
) {
    NavigationHost(navController) {
        composable(Screen.AuctionatorScreen.name) {
            AuctionatorScreen(navController)
        }

        composable(Screen.LoginScreen.name) {
            LoginScreen(navController)
        }

        composable(Screen.CreateAuctionScreen.name) {
            CreateAuctionScreen(navController)
        }

    }.build()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationRailSetup(
    navController: NavController
) {
    val screens = Screen.values().toList()
    val currentScreen by remember {
        navController.currentScreen
    }

    NavigationRail(
        /*modifier = Modifier.align(Alignment.CenterStart).fillMaxHeight()*/
    ) {
        screens.forEach {
            if (it.label != "Login") {
                NavigationRailItem(
                    selected = currentScreen == it.name,
                    icon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.label
                        )
                    },
                    label = {
                        Text(it.label)
                    },
                    alwaysShowLabel = false,
                    onClick = {
                        navController.navigate(it.name)
                    }
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxHeight()
    ) {
        CustomNavigationHost(navController = navController)
    }
}
