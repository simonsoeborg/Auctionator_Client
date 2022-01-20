package factories


object _AuctionID {
    var instance = AuctionIdSingleton()
    var auctionId = ""
}

class AuctionIdSingleton {

    fun setId(arg : String) {
        _AuctionID.auctionId = arg
    }

    fun getId(): String {
        return _AuctionID.auctionId
    }
}