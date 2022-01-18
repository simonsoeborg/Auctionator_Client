package repository

import factories.ConnectionSingleton
import factories.LiveAuctionSingleton
import factories.LoginItems
import model.SpecificAuctionData
import org.jspace.ActualField
import org.jspace.FormalField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jspace.RemoteSpace

class LiveAuctionRepository_impl : LiveAuctionRepository {

    private val currentAuctionSpace = ConnectionSingleton.lobby

    override suspend fun userOnline(auctionId: String) {
        currentAuctionSpace.put("online", auctionId, LoginItems.userName)
    }


    override suspend fun getSpecificAuctionData(auctionId : String) : Flow<SpecificAuctionData> = flow {
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

        val auctionDataObj = SpecificAuctionData(
            auctionId = response[1].toString(),     // AuctionID
            auctionTitle = response[2].toString(), // Title
            auctionPrice = response[3].toString(), // Price
            auctionHighestBid = response[4].toString(), // HighestBid
            auctionTimeRemaining = response[5].toString(), // Timestamp
            auctionDescription = response[6].toString(),
            auctionImageURL = response[7].toString(),
            userName = response[8].toString()
        )

        emit(auctionDataObj)
    }


    override suspend fun updateSpecificAuctionData(auctionId : String, username: String) : Flow<SpecificAuctionData> = flow {
        val response = currentAuctionSpace.query(
            ActualField("auction_"+auctionId),    //0
            ActualField(username),           //1 AuctionID
            FormalField(String::class.java), //2 Title
            FormalField(String::class.java), //3 Price
            FormalField(String::class.java), //4 HighestBid
            FormalField(String::class.java), //5 Timestamp
            FormalField(String::class.java), //6 Description
            FormalField(String::class.java), //7 ImageURL
            FormalField(String::class.java), //8 UserName
        )

        val auctionDataObj = SpecificAuctionData(
            auctionId = response[1].toString(),     // AuctionID
            auctionTitle = response[2].toString(), // Title
            auctionPrice = response[3].toString(), // Price
            auctionHighestBid = response[4].toString(), // HighestBid
            auctionTimeRemaining = response[5].toString(), // Timestamp
            auctionDescription = response[6].toString(),
            auctionImageURL = response[7].toString(),
            userName = response[8].toString()
        )

        emit(auctionDataObj)
    }


    override suspend fun sendBid(bid: String, auctionId: String) {
        currentAuctionSpace.put("bid", auctionId, bid, LoginItems.userName)
    }


    override suspend fun getOnlineClients(): String {
        val response = currentAuctionSpace.query(
            ActualField("onlineclients"),
            FormalField(String::class.java)
        )
        return response[1].toString()
    }

}
