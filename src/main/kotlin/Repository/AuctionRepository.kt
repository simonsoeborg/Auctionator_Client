package Repository

interface AuctionRepository {
    suspend fun queryAuctions()
    suspend fun getAuction(itemName: String)
    suspend fun putAuction()
    suspend fun queryAuction()
    suspend fun createAuction(userName : String, itemName : String, price : Int, endDate : String, endTime : String, description : String)
}