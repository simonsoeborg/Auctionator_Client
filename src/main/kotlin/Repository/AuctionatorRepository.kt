package repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.AuctionData

interface AuctionatorRepository {
    suspend fun createAuction(userName : String, itemName : String, price : String, endTime : String, description : String, imageUrl: String)
    suspend fun getAuction() : Flow<AuctionData>
//    suspend fun getHighestBid(variant : String) : Int
//    suspend fun placeBid()
    fun getAllAuctions() : Flow<AuctionData>
    fun getAllAuctionsOld() : Flow<List<AuctionData>>
}
