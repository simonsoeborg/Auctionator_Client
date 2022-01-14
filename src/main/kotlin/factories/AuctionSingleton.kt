package factories

import Repository.LiveAuctionRepository_impl
import repository.AuctionatorRepository_impl

object HighestBidAuction {
    var highest : Int = 0
    var previousBid : Int = 0
}

object AuctionSingleton {
    lateinit var instance : AuctionatorRepository_impl
}

object LiveAuctionSingleton {
    lateinit var instance : LiveAuctionRepository_impl
}