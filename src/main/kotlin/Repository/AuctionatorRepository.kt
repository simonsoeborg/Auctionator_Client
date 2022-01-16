package repository

import kotlinx.coroutines.flow.Flow
import model.AuctionData

interface AuctionatorRepository {
    suspend fun createAuction(userName : String, itemName : String, price : String, endTime : String, description : String) : String
    suspend fun getAuction(auctionId : String) : String
//    suspend fun getHighestBid(variant : String) : Int
//    suspend fun placeBid()
    suspend fun getAllAuctions() : Flow<List<AuctionData>>
}
