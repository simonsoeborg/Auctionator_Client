package repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jspace.ActualField
import org.jspace.FormalField
import org.jspace.RemoteSpace


class AuctionatorRepository_impl : AuctionatorRepository {

    private val uri = "tcp://127.0.0.1:9001/lobby?keep"
    private val rs = RemoteSpace(uri)

    override suspend fun createAuction(
        userName: String,
        itemName: String,
        price: String,
        endTime: String,
        description: String
    ): String {


         MainScope().launch {
            rs.put(
                "create",
                userName,
                itemName,
                price,
                endTime,
                description
            )
        }


        return rs.get(
            ActualField("auctionURI"),
            ActualField(userName),
            FormalField(String::class.java),
            FormalField(String::class.java)
        ).toString()
    }

    override suspend fun getAuction(variant : String) : Any {
        return rs.queryp(ActualField(variant))[0]
    }

    override suspend fun getHighestBid(variant: String): Int {
        val temp = rs.queryp(ActualField(variant))[1]
        return temp.toString().toInt()
    }

    override suspend fun placeBid() {
    }

    override suspend fun getAllAuctions() {
        TODO("Not yet implemented")
    }

}
