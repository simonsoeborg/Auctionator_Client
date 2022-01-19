package controller

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import factories.LoginItems
import factories._AuctionID
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import model.SpecificAuctionData
import repository.AuctionRepository_impl


class AuctionController(auctionID: String?) {
    private val auctionScope = CoroutineScope(Dispatchers.IO) // Specific Scope for Auctions only
    private val auctionRepo = AuctionRepository_impl()

    init {
        auctionScope.launch {
            if(auctionID != null)
                joinAuction(auctionID)
        }
    }

    private val specificDummyAuctionData: SpecificAuctionData = SpecificAuctionData(
        "null",
        "Test",
        "500",
        "550",
        "255",
        "Test test",
        "null",
        LoginItems.userName
    )

    private val _currentAuction: MutableStateFlow<SpecificAuctionData> = MutableStateFlow(specificDummyAuctionData)
    val currentAuction: StateFlow<SpecificAuctionData> = _currentAuction

    suspend fun joinAuction(auctionID: String) {
        auctionRepo.getSpecificAuctionData(auctionID).collect {
            _currentAuction.value = it
        }
        auctionRepo.userOnline(auctionID)
        refreshAuction(auctionID)
    }

    // TODO This needs to work properly with Coroutine IO
    fun refreshAuction(auctionID: String) {
            auctionScope.launch {
                    println("IOThread: " + Thread.currentThread().name)
                    auctionRepo.updateSpecificAuctionData(auctionID, LoginItems.userName).collect {
                        _currentAuction.value = it
            }
        }
    }

//    // TODO This needs to work properly with Coroutine IO
//    suspend fun refreshAuction(auctionID: String) {
//        var hasRun : Boolean = false
//
//        if(!hasRun) {
//            auctionScope.launch {
//                if (auctionRepo.userOnline(auctionID)) {
//                    println("IOThread: " + Thread.currentThread().name)
//                    auctionRepo.updateSpecificAuctionData(auctionID, LoginItems.userName).collect {
//                        _currentAuction.value = it
//                        println("Highest Bid " + it.auctionHighestBid)
//                        hasRun = true
//                    }
//                } else {
//                    cancel()
//                }
//                delay(1000L)
//            }
//        }
//    }

    fun bidOnAuction(userBid: String) {
        auctionRepo.sendBid(userBid, _currentAuction.value.auctionId)
    }
}
