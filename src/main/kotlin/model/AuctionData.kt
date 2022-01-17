package model

data class AuctionData (
    var auctionId : String,
    var auctionTitle : String,
    val auctionPrice : String,
    val auctionEndTime : String,
    val auctionURI : String
)