package repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.SpecificAuctionData

interface LiveAuctionRepository {

    fun userOnline(auctionId: String)
    fun getSpecificAuctionData(auctionId: String) : Flow<SpecificAuctionData>
    fun updateSpecificAuctionData(auctionId: String, username: String) : Flow<SpecificAuctionData>
    fun sendBid(bid: String, auctionId: String)
    fun getOnlineClients(): String
}
