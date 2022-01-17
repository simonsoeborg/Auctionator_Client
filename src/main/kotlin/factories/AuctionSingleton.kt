package factories

import repository.LiveAuctionRepository_impl
import repository.AuctionatorRepository_impl

object HighestBidAuction {
    var highest : Int = 0
    var previousBid : Int = 0
}

object AuctionSingleton {
    var instance = AuctionatorRepository_impl()
}

object LiveAuctionSingleton {
    var instance = LiveAuctionRepository_impl()
}
