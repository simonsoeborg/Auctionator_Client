package repository

import kotlinx.coroutines.flow.Flow
import model.SpecificAuctionData

interface LiveAuctionRepository {
    suspend fun joinAuction(auctionURI : String)
    suspend fun getSpecificAuctionData() : Flow<SpecificAuctionData>
    suspend fun sendBid(bid: String)
    suspend fun leaveAuction()
    suspend fun userIsActive()
    suspend fun getOnlineClients(): String
}
