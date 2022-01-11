package Controller

import Factories.HighestBidAuction
import kotlinx.coroutines.*
import repository.AuctionRepository_impl

class AuctionBidController {

    suspend fun getHighestBid() : Int {
        val AR = AuctionRepository_impl(0)
        var highestBid : Int = 0
        runBlocking {
            launch {
                highestBid = AR.getAuction()
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