package model

data class JoinAuctionData (
    var auctionURI : String,
    val auctionTitle : String,
    val auctionPrice : String,
    val auctionHighestBid : String,
    val auctionTimeRemaining : String,
    val userName : String
)