package controller

import factories.LoginItems
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    }

    fun joinAuction() {
        _currentAuction.value = auctionRepo.getSpecificAuctionData(auctionID)
        userOnline()
    }

    fun userOnline(): Boolean {
        return auctionRepo.userOnline(auctionID)
    }

    fun leaveAuction(){
        auctionRepo.leaveAuction(auctionID, LoginItems.userName)
        _onlineBidders.value = 0
    }

    fun listenForOnlineBidders(){
        auctionScope.launch {
            _onlineBidders.value = auctionRepo.getOnlineClients().toInt()
        }
    }

    fun listenForNewAuctionData(){
        auctionScope.launch {
            while (true) {
                withTimeout(1500L){
                    try {
                        _currentAuction.value = auctionRepo.updateSpecificAuctionData(auctionID, LoginItems.userName)
                        _onlineBidders.value = auctionRepo.getOnlineClients().toInt()
                    } catch (t : TimeoutCancellationException) {
                        println(t)
                    }
                }
                delay(1000L)
            }
        }
    }

    fun bidOnAuction(userBid: String) {
        auctionRepo.sendBid(userBid, auctionID)
    }

}
