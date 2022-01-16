package controller

import Repository.LiveAuctionRepository_impl
import kotlinx.coroutines.*
import model.JoinAuctionData

class AuctionBidController {

    suspend fun getHighestBid(auctionData: JoinAuctionData) : Int {
        val AR = LiveAuctionRepository_impl()
        var highestBid : Int = 0
        runBlocking {
            launch {
                highestBid = AR.getHighestBid(auctionData)
            }
        }

        return highestBid
    }
}
