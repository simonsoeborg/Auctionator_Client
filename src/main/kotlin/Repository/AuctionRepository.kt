package repository

import kotlinx.coroutines.flow.Flow
import model.SpecificAuctionData

interface AuctionRepository {

    fun userOnline(auctionId: String) : Boolean
    fun getSpecificAuctionData(auctionId: String) : Flow<SpecificAuctionData>
    fun updateSpecificAuctionData(auctionId: String, username: String) : Flow<SpecificAuctionData>
    fun sendBid(bid: String, auctionId: String)
    fun getOnlineClients(): String
}
