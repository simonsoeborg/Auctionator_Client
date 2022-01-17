package repository

import factories.ConnectionSingleton
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
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

    override suspend fun getAuction() : Flow<AuctionData> = flow  {
        ConnectionSingleton.rs.query(
            ActualField("auction"), // auction
            FormalField(String::class.java), // Id
            FormalField(String::class.java), // Title
            FormalField(String::class.java), // EndTime
            FormalField(String::class.java), // Price
            FormalField(String::class.java) // Uri
        ).map {
            val temp = AuctionData(
                auctionId = it.toString(),
                auctionTitle = it.toString(),
                auctionEndTime = it.toString(),
                auctionPrice = it.toString(),
                auctionURI = it.toString()
            )

            emit(temp)
        }
    }

    override fun getAllAuctions() : Flow<AuctionData> = flow {

        ConnectionSingleton.rs.queryAll(
            ActualField("auction"), // auction
            FormalField(String::class.java), // Id
            FormalField(String::class.java), // Title
            FormalField(String::class.java), // EndTime
            FormalField(String::class.java), // Price
            FormalField(String::class.java) // Uri
        ).map {
            val temp = AuctionData(
                auctionId = it[1].toString(),
                auctionTitle = it[2].toString(),
                auctionEndTime = it[3].toString(),
                auctionPrice = it[4].toString(),
                auctionURI = it[5].toString()
            )

            emit(temp)
        }
    }

    override fun getAllAuctionsOld() : Flow<List<AuctionData>> = flow {
        val response = ConnectionSingleton.rs.queryAll(
            ActualField("auction"), // auction
            FormalField(String::class.java), // Id
            FormalField(String::class.java), // Title
            FormalField(String::class.java), // EndTime
            FormalField(String::class.java), // Price
            FormalField(String::class.java) // Uri
        )

        val auctionList = mutableListOf<AuctionData>()

        response.map{
            auctionList.add(
                AuctionData(
                    auctionId = it[1].toString(),
                    auctionTitle = it[2].toString(),
                    auctionEndTime = it[3].toString(),
                    auctionPrice = it[4].toString(),
                    auctionURI = it[5].toString()
                )
            )
            emit(auctionList)
        }
    }
}
