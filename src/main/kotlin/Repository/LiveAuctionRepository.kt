package Repository

import kotlinx.coroutines.flow.Flow
import model.SpecificAuctionData

interface LiveAuctionRepository {
    suspend fun joinAuction(auctionURI : String)
    suspend fun getSpecificAuctionData(auctionURI: String) : Flow<SpecificAuctionData>
    suspend fun sendBid(auctionData: SpecificAuctionData, newHighestBid: Int)
}