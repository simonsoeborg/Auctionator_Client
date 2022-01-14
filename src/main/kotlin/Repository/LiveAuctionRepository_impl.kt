package Repository

import factories.ConnectionSingleton
import factories.LoginItems
import model.JoinAuctionData
import org.jspace.ActualField
import org.jspace.FormalField

class LiveAuctionRepository_impl : LiveAuctionRepository {
    override suspend fun joinAuction(auctionURI: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAuctionDetails(auctionURI: String): JoinAuctionData {
        val response = ConnectionSingleton.rs.query(
            ActualField(auctionURI), // URI
            FormalField(String::class.java), // Title
            FormalField(String::class.java), // Price
            FormalField(String::class.java), // HighestBid
            FormalField(String::class.java) // auctionTimeRemaining
        )

        return JoinAuctionData(
            response[0].toString(),
            response[1].toString(),
            response[2].toString(),
            response[3].toString(),
            response[4].toString(),
            LoginItems.userName
        )
    }

    override suspend fun getHighestBid(auctionData: JoinAuctionData) : Int {
        return auctionData.auctionPrice.toInt()
    }

    override suspend fun sendBid(auctionData: JoinAuctionData, newHighestBid: Int) {
        ConnectionSingleton.rs.put(
            auctionData.auctionURI,
            auctionData.userName,
            newHighestBid.toString()
        )
    }
}