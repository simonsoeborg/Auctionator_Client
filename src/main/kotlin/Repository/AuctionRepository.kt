package Repository

interface AuctionRepository {
    suspend fun createAuction(userName : String, itemName : String, price : Int, endDate : String, endTime : String, description : String)
    suspend fun getAuction()
    suspend fun placeBid()
}