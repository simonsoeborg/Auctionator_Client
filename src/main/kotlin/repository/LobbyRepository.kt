package repository

import kotlinx.coroutines.flow.Flow
import model.AuctionData

interface LobbyRepository {
    fun createAuction(username : String, auctionTitle : String, price : String, endTime : String, description : String, imageUrl: String)
    fun getAllAuctions() : Flow<List<AuctionData>>
}
