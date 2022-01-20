package repository

import model.SpecificAuctionData

interface AuctionRepository {
    fun userOnline(auctionId: String) : Boolean
    fun getSpecificAuctionData(auctionId: String) : SpecificAuctionData
    fun updateSpecificAuctionData(auctionId: String, username: String): SpecificAuctionData
    fun sendBid(bid: String, auctionId: String)
    fun getOnlineClients(): String
    fun leaveAuction(auctionId: String, username: String)
}
