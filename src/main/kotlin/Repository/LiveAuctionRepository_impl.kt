package Repository

import factories.ConnectionSingleton
import model.SpecificAuctionData
import org.jspace.ActualField
import org.jspace.FormalField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LiveAuctionRepository_impl : LiveAuctionRepository {
    override suspend fun joinAuction(auctionURI: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getSpecificAuctionData(auctionURI: String) : Flow<SpecificAuctionData> = flow {
        val response = ConnectionSingleton.rs.query(
            ActualField(auctionURI), // URI
            FormalField(String::class.java), // Title
            FormalField(String::class.java), // Price
            FormalField(String::class.java), // HighestBid
            FormalField(String::class.java), // auctionTimeRemaining
            FormalField(String::class.java), // Description
            FormalField(String::class.java), // ImageURL
            FormalField(String::class.java) // UserName
        )

        val auctionDataObj = SpecificAuctionData(
            auctionURI = response[0].toString(), // URI
            auctionTitle = response[1].toString(), // Title
            auctionPrice = response[2].toString(), // Price
            auctionHighestBid = response[3].toString(), // HighestBid
            auctionTimeRemaining = response[4].toString(), // Time Remaining
            auctionDescription = response[5].toString(),
            auctionImageURL = response[6].toString(),
            userName = response[7].toString()
        )

        emit(auctionDataObj)
    }

    override suspend fun sendBid(auctionData: SpecificAuctionData, newHighestBid: Int) {
        ConnectionSingleton.rs.put(
            auctionData.auctionURI,
            auctionData.userName,
            newHighestBid.toString()
        )
    }
}