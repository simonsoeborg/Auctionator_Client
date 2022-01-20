package model

data class AuctionData (
    var auctionId : String?,
    var auctionTitle : String,
    val auctionPrice : String,
    val highestBid : String,
    val auctionEndTime : String,
    val description : String,
    val imageURL : String,
    val auctionCreator : String
)
