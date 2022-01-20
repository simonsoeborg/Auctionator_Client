package controller

import factories.LoginItems
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import model.SpecificAuctionData
import repository.AuctionRepository_impl


class AuctionController {
    private val auctionScope = CoroutineScope(Dispatchers.IO) // Specific Scope for Auctions only
    private val auctionRepo = AuctionRepository_impl()
    var auctionID = "null"

    private val specificDummyAuctionData: SpecificAuctionData = SpecificAuctionData(
        "null",
        "Test",
        "500",
        "550",
        "255",
        "Test test",
        "https://hvidevareshoppen.dk/images/BraunKF3120Hvid.jpg",
        LoginItems.userName
    )

    private val _currentAuction: MutableStateFlow<SpecificAuctionData> = MutableStateFlow(specificDummyAuctionData)
    val currentAuction: StateFlow<SpecificAuctionData> = _currentAuction

    private val _onlineBidders: MutableStateFlow<Int> = MutableStateFlow(0)
    val onlineBidders: StateFlow<Int> = _onlineBidders

    init {
        listenForOnlineBidders()
        println("AuctionController bliver oprettet")
        //joinAuction()
        //listenForOnlineBidders()
    }

    fun joinAuction() {
        _currentAuction.value = auctionRepo.getSpecificAuctionData(auctionID)
    }

    fun userOnline() {
        auctionRepo.userOnline(auctionID)
    }

    fun listenForOnlineBidders(){
        auctionScope.launch {
            _onlineBidders.value = auctionRepo.getOnlineClients().toInt()
        }
    }

    fun listenForNewAuctionData(){
        auctionScope.launch {
            println("IOThread: " + Thread.currentThread().name)
            auctionRepo.updateSpecificAuctionData(auctionID, LoginItems.userName).collect {
                _currentAuction.value = it
            }
        }
    }

    fun bidOnAuction(userBid: String) {
        auctionRepo.sendBid(userBid, auctionID)
    }

    operator fun invoke(): AuctionController {
        return this
    }
}
