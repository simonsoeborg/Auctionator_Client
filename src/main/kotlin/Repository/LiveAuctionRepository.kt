package repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.SpecificAuctionData

interface LiveAuctionRepository {

    suspend fun userOnline(auctionId: String)
    suspend fun getSpecificAuctionData(auctionId: String) : Flow<SpecificAuctionData>
    suspend fun updateSpecificAuctionData(auctionId: String, username: String) : Flow<SpecificAuctionData>
    suspend fun sendBid(bid: String, auctionId: String)
    suspend fun getOnlineClients(): String
}
