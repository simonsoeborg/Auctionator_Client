package controller

import factories.LoginItems
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import model.SpecificAuctionData
import repository.AuctionRepository_impl


class AuctionController(private val auctionID: String) {
    private val auctionScope = CoroutineScope(Dispatchers.IO) // Specific Scope for Auctions only
    private val auctionRepo = AuctionRepository_impl()

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

    init {
        println("AuctionController bliver oprettet")
        joinAuction()
    }

    private fun joinAuction() {
        _currentAuction.value = auctionRepo.getSpecificAuctionData(auctionID)
    }

    fun userOnline() {
        auctionRepo.userOnline(auctionID)
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
        auctionRepo.sendBid(userBid, _currentAuction.value.auctionId)
    }

    operator fun invoke(): AuctionController {
        return this
    }
}
