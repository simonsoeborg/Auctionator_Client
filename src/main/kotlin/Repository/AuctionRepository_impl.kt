package repository

import org.jspace.ActualField
import org.jspace.RemoteSpace


class AuctionRepository_impl(auctionPort: Int) : AuctionRepository {

    var uri = "tcp://85.204.194.92:9001/lobby?keep/auction?$auctionPort"
    val auctionHandler = RemoteSpace(uri)

    override suspend fun createAuction(userName : String, itemName : String, price : Int, endDate : String, endTime : String, description : String) {
        auctionHandler.put(ActualField("create"),
            ActualField(userName),  // Username
            ActualField(itemName),  // Itemname
            ActualField(price), // Start price
            ActualField(endDate),  // End-date
            ActualField(endTime),  // End-time
            ActualField(description),  // Description
        )
    }

    override suspend fun getAuction() : Int {
        return 0
    }

    override suspend fun placeBid() {
    }

}
