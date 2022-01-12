package controller

import factories.HighestBidAuction
import kotlinx.coroutines.*
import repository.AuctionRepository_impl

class AuctionBidController {

    suspend fun getHighestBid() : Int {
        val AR = AuctionRepository_impl(0)
        var highestBid : Int = 0
        runBlocking {
            launch {
                highestBid = AR.getHighestBid("TupleSpace / Auction / Bid?")
            }
        }

        return highestBid
    }

    fun compareBids(newBid : Int) : Boolean {
        if(newBid > HighestBidAuction.highest) {
            HighestBidAuction.previousBid = HighestBidAuction.highest
            HighestBidAuction.highest = newBid
            return true
        }

        // Wierd Edge-case
        if(newBid < HighestBidAuction.highest && newBid > HighestBidAuction.previousBid) {
            HighestBidAuction.previousBid = newBid
        }

        return false
    }
}
