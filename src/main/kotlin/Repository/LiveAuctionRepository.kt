package repository

import kotlinx.coroutines.flow.Flow
import model.SpecificAuctionData

interface LiveAuctionRepository {
    suspend fun joinAuction(auctionURI : String)
    suspend fun getSpecificAuctionData() : Flow<SpecificAuctionData>
    suspend fun sendBid(bid: String)
}
