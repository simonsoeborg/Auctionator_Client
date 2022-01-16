package repository

import factories.ConnectionSingleton
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import model.AuctionData
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

    override suspend fun getAllAuctions() : Flow<List<AuctionData>> = flow {
        val response = ConnectionSingleton.rs.queryAll(
            ActualField("create"), // Kan ikke huske, hvad disse vil hedde
            FormalField(String::class.java), // Title
            FormalField(String::class.java), // Amount of Bidders
            FormalField(String::class.java), // Price
        )

        val auctionList = mutableListOf<AuctionData>()

        response.forEach{
            auctionList.add(
                AuctionData(
                    auctionTitle = it[0].toString(),
                    auctionAmountOfBidders = it[1].toString(),
                    auctionPrice = it[2].toString()
                )
            )
        }

        emit(auctionList)
    }

}
