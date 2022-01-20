package repository

import factories.ConnectionSingleton
import factories.LoginItems
import model.SpecificAuctionData
import org.jspace.ActualField
import org.jspace.FormalField

class AuctionRepository_impl : AuctionRepository {

    private val currentAuctionSpace = ConnectionSingleton.lobby

    override fun userOnline(auctionId: String) : Boolean {
        return currentAuctionSpace.put("online", auctionId, LoginItems.userName)
    }

    override fun getSpecificAuctionData(auctionId: String): SpecificAuctionData {
        currentAuctionSpace.put("hello", auctionId, LoginItems.userName)
        val response = currentAuctionSpace.query(
            ActualField("auction"),    //0
            ActualField(auctionId),          //1 AuctionID
            FormalField(String::class.java), //2 Title
            FormalField(String::class.java), //3 Price
            FormalField(String::class.java), //4 HighestBid
            FormalField(String::class.java), //5 Timestamp
            FormalField(String::class.java), //6 Description
            FormalField(String::class.java), //7 ImageURL
            FormalField(String::class.java), //8 UserName
        )

        return SpecificAuctionData(
            auctionId = response[1].toString(),     // AuctionID
            auctionTitle = response[2].toString(), // Title
            auctionPrice = response[3].toString(), // Price
            auctionHighestBid = response[4].toString(), // HighestBid
            auctionTimeRemaining = response[5].toString(), // Timestamp
            auctionDescription = response[6].toString(), // Description
            auctionImageURL = response[7].toString(), // ImageURL
            userName = response[8].toString() // AuctionCreator
        )
    }

    override fun updateSpecificAuctionData(auctionId: String, username: String): SpecificAuctionData {
        val specificAuctionResponse = currentAuctionSpace.query(
            ActualField("auction_$auctionId"),    //0
            ActualField(username),           //1 AuctionID
            FormalField(String::class.java), //2 Title
            FormalField(String::class.java), //3 Price
            FormalField(String::class.java), //4 HighestBid
            FormalField(String::class.java), //5 Timestamp
            FormalField(String::class.java), //6 Description
            FormalField(String::class.java), //7 ImageURL
            FormalField(String::class.java), //8 UserName
        )

        return SpecificAuctionData(
            auctionId = specificAuctionResponse[1].toString(),     // AuctionID
            auctionTitle = specificAuctionResponse[2].toString(), // Title
            auctionPrice = specificAuctionResponse[3].toString(), // Price
            auctionHighestBid = specificAuctionResponse[4].toString(), // HighestBid
            auctionTimeRemaining = specificAuctionResponse[5].toString(), // Timestamp
            auctionDescription = specificAuctionResponse[6].toString(), // Description
            auctionImageURL = specificAuctionResponse[7].toString(), // ImageURL
            userName = specificAuctionResponse[8].toString() // AuctionCreator
        )
    }

    override fun sendBid(bid: String, auctionId: String) {
        if (currentAuctionSpace.put("bid", auctionId, bid, LoginItems.userName)){
            println("auctionId bid: "+auctionId + "username " +LoginItems.userName)
        }
    }

    override fun leaveAuction(auctionId: String, username: String) {
         val res = currentAuctionSpace.get(
            ActualField("online"),
            ActualField(auctionId),
            ActualField(username)
        )

        println("leaving auction: " + res[1])
    }

    override fun getOnlineClients(): String {
        val response = currentAuctionSpace.query(
            ActualField("onlineclients"),
            FormalField(String::class.java)
        )
        return response[1].toString()
    }
}
