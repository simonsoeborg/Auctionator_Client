package controller

import factories.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import model.AuctionData
import model.SpecificAuctionData
import java.text.SimpleDateFormat
import java.util.*

class MainController {


    private val specificDummyAuctionData: SpecificAuctionData = SpecificAuctionData(
        "null",
        "Test",
        "500",
        "550",
        "255",
        "Test test",
        "null",
        LoginItems.userName
    )

    private val _allAuctions: MutableStateFlow<List<AuctionData>> = MutableStateFlow(emptyList())
    val allAuctions: StateFlow<List<AuctionData>> = _allAuctions

    private val _currentAuction: MutableStateFlow<SpecificAuctionData> = MutableStateFlow(specificDummyAuctionData)
    val currentAuction: StateFlow<SpecificAuctionData> = _currentAuction


    init {
        GlobalScope.launch(Dispatchers.Unconfined) {
            getAllAuctions()
        }
    }

    private suspend fun getAllAuctions() {
                AuctionSingleton.instance.getAllAuctions().collect {
                    _allAuctions.value = it
        }
    }

    suspend fun refreshAuctions() {
        getAllAuctions()
    }

    fun joinAuction(auctionID: String) {
        GlobalScope.launch(Dispatchers.Default){
            LiveAuctionSingleton.instance.getSpecificAuctionData(auctionID).collect {
                _currentAuction.value = it
            }
        }
        refreshAuction(auctionID)
    }

     fun refreshAuction(auctionID: String){

        GlobalScope.launch(Dispatchers.IO){

            LiveAuctionSingleton.instance.userOnline(auctionID)

            withContext(Dispatchers.IO){
                LiveAuctionSingleton.instance.updateSpecificAuctionData(auctionID, LoginItems.userName).collect {
                    _currentAuction.value = it
                }
            }
        }
    }

     fun createAuction(
        auctionTitle: String,
        auctionPrice: String,
        auctionTime: String,
        description: String,
        imageUrl: String
    ) {
        AuctionSingleton.instance.createAuction(
            LoginItems.userName,
            auctionTitle,
            auctionPrice,
            auctionTime,
            description,
            imageUrl
        )
    }

   fun bidOnAuction(userBid: String) {
        LiveAuctionSingleton.instance.sendBid(userBid, _currentAuction.value.auctionId)
    }

    fun deleteAuction() {

    }


    private  val _isRunning : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isRunning : StateFlow<Boolean> = _isRunning

    private  val _hours : MutableStateFlow<Int> = MutableStateFlow(0)
    val hours : MutableStateFlow<Int> = _hours

    private  val _mins : MutableStateFlow<Int> = MutableStateFlow(0)
    val mins : MutableStateFlow<Int> = _mins

    private  val _seconds : MutableStateFlow<Int> = MutableStateFlow(0)
    val seconds : MutableStateFlow<Int> = _seconds


    fun updateTimer () {
        val totalEndTime = currentAuction.value.auctionTimeRemaining

        val initialDate = Calendar.getInstance() // Current DateTime
        initialDate.timeZone = TimeZone.getTimeZone("GMT+1") // Set TimeZone

        // Format date so it matches the pattern from auctionTimeRemaining:
        val formatter = SimpleDateFormat("HH:mm:ss")
        val currentTimeStamp = formatter.format(initialDate.time)
        val endTime = formatter.format(totalEndTime)

        //endTime
    }
}

