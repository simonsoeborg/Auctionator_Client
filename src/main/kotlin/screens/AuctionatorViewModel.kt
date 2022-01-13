package screens

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import repository.AuctionatorRepository

class auctionatorViewModel(private val repository: AuctionatorRepository) {

    private val _tupleTest = MutableStateFlow(listOf(""))
    val tupleTest : StateFlow<List<String>> = _tupleTest

    init {
        GlobalScope.launch {
        println(newAuctionTupleTest())
        }
    }

    private suspend fun newAuctionTupleTest(): String {
        var newAuctionURI = "null"

        GlobalScope.async(Dispatchers.Default) {
            repository.createAuction(
                "Simon",
                "EDB Maskine",
                "1000",
                "13:02",
                "En Computer"
            ).also { newAuctionURI = it }
        }.await()

        return newAuctionURI
    }
}
