package controller

import factories.AuctionSingleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import model.AuctionData

class MainController {

    init {
        getAllAuctions()
    }

    val dummyAuctionData : AuctionData = AuctionData("Hejsa","50", "500", "5", "uri")

    private val _allAuctions : MutableStateFlow<List<AuctionData>> = MutableStateFlow(listOf(dummyAuctionData))
    val allAuctions : StateFlow<List<AuctionData>> = _allAuctions

    private val _currentAuction : MutableStateFlow<AuctionData> = MutableStateFlow(dummyAuctionData)
    val currentAuction : StateFlow<AuctionData> = _currentAuction

    fun getAuction() {
        // AuctionSingleton.instance.getAuction()
    }

    fun getAllAuctions() {
        runBlocking {
            launch {
//                AuctionSingleton.instance.getAllAuctions().collect {
//                    _allAuctions.value = it
//                }
                AuctionSingleton.instance.getAuction().collect {
                    _currentAuction.value = it
                }
            }
        }
    }


    fun createAuction() {

    }

    fun bidOnAuction() {

    }

    fun deleteAuction() {

    }
}