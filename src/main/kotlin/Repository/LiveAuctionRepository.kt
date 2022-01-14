package Repository

import model.JoinAuctionData

interface LiveAuctionRepository {
    suspend fun joinAuction(auctionURI : String)
    suspend fun getAuctionDetails(auctionURI: String) : JoinAuctionData
    suspend fun getHighestBid(auctionData: JoinAuctionData) : Int
    suspend fun sendBid(auctionData: JoinAuctionData, newHighestBid: Int)
}