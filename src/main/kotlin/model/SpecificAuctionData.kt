package model

data class SpecificAuctionData (
    var auctionURI : String,
    var auctionTitle : String,
    val auctionPrice : String,
    val auctionHighestBid : String,
    val auctionTimeRemaining : String,
    val auctionDescription : String,
    val auctionImageURL : String,
    val userName : String
)