package repository

import kotlinx.coroutines.flow.Flow
import model.AuctionData

interface LobbyRepository {
    fun createAuction(userName : String, itemName : String, price : String, endTime : String, description : String, imageUrl: String)
    fun getAuction() : Flow<AuctionData>
    fun getAllAuctions() : Flow<List<AuctionData>>
}
