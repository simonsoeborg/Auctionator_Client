package repository

import factories.ConnectionSingleton
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jspace.ActualField
import org.jspace.FormalField


class AuctionatorRepository_impl : AuctionatorRepository {

    override suspend fun createAuction(
        userName: String,
        itemName: String,
        price: String,
        endTime: String,
        description: String
    ): String {


         MainScope().launch {
            ConnectionSingleton.rs.put(
                "create",
                userName,
                itemName,
                price,
                endTime,
                description
            )
        }

        return ConnectionSingleton.rs.get(
            ActualField("auctionURI"),
            ActualField(userName),
            FormalField(String::class.java),
            FormalField(String::class.java)
        ).toString()
    }

    override suspend fun getAuction(auctionId : String) : String {
        return ConnectionSingleton.rs.queryp(ActualField(auctionId)).toString()
    }

    override suspend fun getAllAuctions() {
        TODO("Not yet implemented")
    }

}
