package repository

import factories.LoginItems
import model.SpecificAuctionData
import org.jspace.ActualField
import org.jspace.FormalField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jspace.RemoteSpace

class LiveAuctionRepository_impl : LiveAuctionRepository {

    private var currentAuctionSpace: RemoteSpace = RemoteSpace("tcp://127.0.0.1:9001/lobby?keep")
    private var currentAuctionSpaceURI: String = "tcp://127.0.0.1:9001/lobby?keep"

    override suspend fun joinAuction(auctionURI: String) {
        currentAuctionSpaceURI = auctionURI
        setCurrentAuctionSpace(auctionURI)
        currentAuctionSpace.put(
            "hello",
            LoginItems.userName
        )
    }

    override suspend fun leaveAuction() {
        currentAuctionSpace.get(
            ActualField("online"),
            ActualField(LoginItems.userName)
        )
    }

    override suspend fun userIsActive() {
        currentAuctionSpace.put(
            "online",
            LoginItems.userName
        )
    }

    override suspend fun getSpecificAuctionData() : Flow<SpecificAuctionData> = flow {
        val response = currentAuctionSpace.get(
            ActualField("auctiondata"), //0
            ActualField(LoginItems.userName),//1
            FormalField(String::class.java), //2 Title
            FormalField(String::class.java), //3 Price
            FormalField(String::class.java), //4 HighestBid
            FormalField(String::class.java), //5 Timestamp
            FormalField(String::class.java), //6 Description
            FormalField(String::class.java), //7 ImageURL
            FormalField(String::class.java), //8 UserName
        )

        val auctionDataObj = SpecificAuctionData(
            auctionURI = currentAuctionSpaceURI, // URI
            auctionTitle = response[2].toString(), // Title
            auctionPrice = response[3].toString(), // Price
            auctionHighestBid = response[4].toString(), // HighestBid
            auctionTimeRemaining = response[5].toString(), // Timestamp
            auctionDescription = response[6].toString(),
            auctionImageURL = response[7].toString(),
            userName = response[8].toString()
        )
        userIsActive()
        emit(auctionDataObj)
    }

    override suspend fun sendBid(bid: String) {
        currentAuctionSpace.put("bid",bid,LoginItems.userName)
    }


    override suspend fun getOnlineClients(): String {
        val response = currentAuctionSpace.query(
            ActualField("onlineclients"),
            FormalField(String::class.java)
        )
        return response[1].toString()
    }

    private fun setCurrentAuctionSpace(uri: String) {
        println("Connecting to RemoteSpace $uri...")
        currentAuctionSpace =  RemoteSpace(uri)
    }


}
