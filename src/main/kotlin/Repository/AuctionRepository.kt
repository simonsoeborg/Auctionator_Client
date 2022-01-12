package repository

interface AuctionRepository {
    suspend fun createAuction(aId : Int, userName : String, itemName : String, price : Int, endDate : String, endTime : String, imgURL : String)
    suspend fun getAuction(variant : String) : Any
    suspend fun getHighestBid(variant : String) : Int
    suspend fun placeBid()
}
