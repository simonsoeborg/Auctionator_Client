package repository

import factories.ConnectionSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import model.AuctionData
import org.jspace.ActualField
import org.jspace.FormalField


class LobbyRepository_impl : LobbyRepository {

    private val lobbyScope = CoroutineScope(Dispatchers.IO)

    override fun createAuction(
        username: String,
        auctionTitle: String,
        price: String,
        endTime: String,
        description: String,
        imageUrl: String
    ) {
        lobbyScope.launch {
            ConnectionSingleton.lobby.put(
                "create",
                username,
                auctionTitle,
                price,
                endTime,
                description,
                imageUrl
            )
        }
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
                    auctionPrice = it[3].toString(),
                    highestBid = it[4].toString(),
                    auctionEndTime = it[5].toString(),
                    description = it[6].toString(),
                    imageURL = it[7].toString(),
                    auctionCreator = it[8].toString()
                )
            )
        }
        emit(auctionList)
    }
}
