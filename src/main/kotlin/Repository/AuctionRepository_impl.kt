package Repository

import org.jspace.ActualField
import org.jspace.FormalField
import org.jspace.RemoteSpace
import java.net.InetAddress




class AuctionRepository_impl : AuctionRepository {

    var uri = "tcp://" + "85.204.194.92" + "/lobby?keep"
    val lobby = RemoteSpace(uri)

    override suspend fun queryAuctions() {
        lobby.queryAll(
            FormalField(String::class.java),
            FormalField(String::class.java),
            FormalField(String::class.java),
            FormalField(String::class.java),
            FormalField(Int::class.java),
            FormalField(String::class.java),
            FormalField(String::class.java),
            FormalField(String::class.java),
        )
    }

    override suspend fun getAuction(itemName: String) {
        lobby.get(
            ActualField("create"),
            FormalField(String::class.java),
            ActualField(itemName),
            FormalField(String::class.java),
            FormalField(Int::class.java),
            FormalField(String::class.java),
            FormalField(String::class.java),
            FormalField(String::class.java)
        )
    }

    override suspend fun putAuction() {
        TODO("Not yet implemented")
    }

    override suspend fun queryAuction() {
        TODO("Not yet implemented")
    }

    override suspend fun createAuction(userName : String, itemName : String, price : Int, endDate : String, endTime : String, description : String) {
        lobby.put(ActualField("create"),
            ActualField(userName),  // Username
            ActualField(itemName),  // Itemname
            ActualField(price), // Start price
            ActualField(endDate),  // End-date
            ActualField(endTime),  // End-time
            ActualField(description),  // Description
        )
    }

}