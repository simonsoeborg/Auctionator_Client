package navigation

import AuctionatorScreen
import LoginScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import controller.MainController
import factories.LoginItems
import screens.AuctionScreen
import screens.CreateAuctionScreen


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
    ),
    AuctionScreen(
        label = "Auction",
        icon = Icons.Filled.Notifications
    )
}


@Composable
fun CustomNavigationHost(
    navController: NavController,
    mainController: MainController
) {
    NavigationHost(navController) {
        composable(Screen.AuctionatorScreen.name) {
            AuctionatorScreen(navController, mainController)
        }

        composable(Screen.LoginScreen.name) {
            LoginScreen(navController, mainController)
        }

        composable(Screen.CreateAuctionScreen.name) {
            CreateAuctionScreen(navController, mainController)
        }

        composable(Screen.AuctionScreen.name) {
            // TODO: Figure out how to pass auctionId to AuctionScreen
            AuctionScreen(navController, mainController)
        }

    }.build()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationRailSetup(
    navController: NavController,
    mainController: MainController
) {
    val screens = Screen.values().toList()
    val currentScreen by remember {
        navController.currentScreen
    }

    NavigationRail(
        /*modifier = Modifier.align(Alignment.CenterStart).fillMaxHeight()*/
    ) {
        screens.forEach {
            if (it.label != "Auction") {
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
        CustomNavigationHost(navController = navController, mainController)
    }
}
