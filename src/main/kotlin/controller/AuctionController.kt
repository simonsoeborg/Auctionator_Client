package controller

import factories.LoginItems
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import model.SpecificAuctionData
import repository.AuctionRepository_impl

class AuctionController() {
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

    fun joinAuction(auctionID: String) {
        auctionScope.launch{
            auctionRepo.getSpecificAuctionData(auctionID).collect {
                _currentAuction.value = it
            }
        }
        // refreshAuction(auctionID)
    }

    // TODO This needs to work properly with Coroutine IO
    fun refreshAuction(auctionID: String){

        auctionScope.launch {

            auctionRepo.userOnline(auctionID)

            withContext(Dispatchers.IO){
                auctionRepo.updateSpecificAuctionData(auctionID, LoginItems.userName).collect {
                    _currentAuction.value = it
                }
            }
        }
    }

    fun bidOnAuction(userBid: String) {
        auctionRepo.sendBid(userBid, _currentAuction.value.auctionId)
    }

}