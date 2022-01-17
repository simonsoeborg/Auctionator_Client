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

    override suspend fun getSpecificAuctionData() : Flow<SpecificAuctionData> = flow {
        val response = currentAuctionSpace.query(
            ActualField("initialdata"), //0
            ActualField(LoginItems.userName),//1
            FormalField(String::class.java), //2 Title
            FormalField(String::class.java), //3 Price
            FormalField(String::class.java), //4 HighestBid
            FormalField(String::class.java), //5 auctionTimeRemaining
            FormalField(String::class.java), //6 Description
            FormalField(String::class.java), //7 ImageURL
            FormalField(String::class.java)  //8 UserName
        )

        val auctionDataObj = SpecificAuctionData(
            auctionURI = currentAuctionSpaceURI, // URI
            auctionTitle = response[2].toString(), // Title
            auctionPrice = response[3].toString(), // Price
            auctionHighestBid = response[4].toString(), // HighestBid
            auctionTimeRemaining = response[5].toString(), // Time Remaining
            auctionDescription = response[6].toString(),
            auctionImageURL = response[7].toString(),
            userName = response[8].toString()
        )
        println("-------------------------" +response[6].toString())
        println("-------------------------" +response[2].toString())

        emit(auctionDataObj)
    }

    override suspend fun sendBid(bid: String) {
        currentAuctionSpace.put("bid",bid,LoginItems.userName)
    }

    private fun setCurrentAuctionSpace(uri: String) {
        println("Connecting to RemoteSpace $uri...")
        currentAuctionSpace =  RemoteSpace(uri)
    }
}
