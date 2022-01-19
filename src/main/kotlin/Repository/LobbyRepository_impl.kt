package repository

import factories.ConnectionSingleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.AuctionData
import org.jspace.ActualField
import org.jspace.FormalField


class LobbyRepository_impl : LobbyRepository {

    override fun createAuction(
        userName: String,
        auctionTitle: String,
        price: String,
        endTime: String,
        description: String,
        imageUrl: String
    ) {

        ConnectionSingleton.lobby.put(
            "create",
            userName,
            auctionTitle,
            price,
            endTime,
            description,
            imageUrl
        )
    }

    override fun getAuction() : Flow<AuctionData> = flow  {
        val response = ConnectionSingleton.lobby.query(
            ActualField("auction"), // auction
            FormalField(String::class.java), // Id
            FormalField(String::class.java), // Title
            FormalField(String::class.java), // EndTime
            FormalField(String::class.java), // Price
            FormalField(String::class.java) // Uri
        )

        val temp = AuctionData(
            auctionId = response[1].toString(),
            auctionTitle = response[2].toString(),
            auctionEndTime = response[3].toString(),
            auctionPrice = response[4].toString(),
            auctionURI = response[5].toString()
        )

        emit(temp)
        }


    override fun getAllAuctions() : Flow<List<AuctionData>> = flow {
        val response = ConnectionSingleton.lobby.queryAll(
            ActualField("auction"),    //0
            FormalField(String::class.java), //1 AuctionID
            FormalField(String::class.java), //2 Title
            FormalField(String::class.java), //3 Price
            FormalField(String::class.java), //4 HighestBid
            FormalField(String::class.java), //5 Timestamp
            FormalField(String::class.java), //6 Description
            FormalField(String::class.java), //7 ImageURL
            FormalField(String::class.java), //8 Auction creator
        )

        val auctionList = mutableListOf<AuctionData>()

        response.map{
            auctionList.add(
                AuctionData(
                    auctionId = it[1].toString(),
                    auctionTitle = it[2].toString(),
                    auctionEndTime = it[3].toString(),
                    auctionPrice = it[4].toString(),
                    auctionURI = it[5].toString()
                )
            )
        }
        emit(auctionList)
    }
}
