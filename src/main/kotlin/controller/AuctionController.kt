package controller

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
    }

    // TODO This needs to work properly with Coroutine IO
    suspend fun refreshAuction(auctionID: String) {
        if (auctionRepo.userOnline(auctionID))
            auctionRepo.updateSpecificAuctionData(auctionID, LoginItems.userName).collect {
                _currentAuction.value = it
            }
    }

    fun bidOnAuction(userBid: String) {
        auctionRepo.sendBid(userBid, _currentAuction.value.auctionId)

        auctionScope.launch {
            refreshAuction(_AuctionID.instance.getId())
        }
    }

}