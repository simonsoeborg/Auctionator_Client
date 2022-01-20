package controller

import factories.LobbySingleton
import factories.LoginItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.AuctionData

class LobbyController {
    private val lobbyScope = CoroutineScope(Dispatchers.IO) // Specific Scope for Lobby only

    init {
        lobbyScope.launch {
            getAllAuctions()
        }
    }

    private val _allAuctions: MutableStateFlow<List<AuctionData>> = MutableStateFlow(emptyList())
    val allAuctions: StateFlow<List<AuctionData>> = _allAuctions

    private suspend fun getAllAuctions() {
        LobbySingleton.instance.getAllAuctions().collect {
            _allAuctions.value = it
        }
    }

    fun refreshAllAuctions() {
        lobbyScope.launch {
            LobbySingleton.instance.getAllAuctions().collect {
                _allAuctions.value = it
                println("Collected new value for allAuctions @ Lobbyscreen")
            }
        }
    }

    fun createAuction(
        auctionTitle: String,
        auctionPrice: String,
        auctionTime: String,
        description: String,
        imageUrl: String
    ) {
        LobbySingleton.instance.createAuction(
            LoginItems.userName,
            auctionTitle,
            auctionPrice,
            auctionTime,
            description,
            imageUrl
        )
    }
}
