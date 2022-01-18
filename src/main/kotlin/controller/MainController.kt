package controller

import com.sun.jdi.IntegerType
import factories.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import model.AuctionData
import model.SpecificAuctionData
import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class MainController {


    private val dummyAuctionData: AuctionData = AuctionData("Hejsa", "50", "500", "5", "uri")
    private val specificDummyAuctionData: SpecificAuctionData = SpecificAuctionData(
        "tcp://127.0.0.1:9001/auction1?keep",
        "Test",
        "500",
        "550",
        "255",
        "Test test",
        "https://manchetknapperne.dk/wp/wp-content/uploads/2020/09/Pin-skipper-skr%C3%A6k-b.jpg",
        LoginItems.userName
    )

    private val _allAuctions: MutableStateFlow<List<AuctionData>> = MutableStateFlow(listOf(dummyAuctionData))
    val allAuctions: StateFlow<List<AuctionData>> = _allAuctions

    private val _currentAuction: MutableStateFlow<SpecificAuctionData> = MutableStateFlow(specificDummyAuctionData)
    val currentAuction: StateFlow<SpecificAuctionData> = _currentAuction


    init {
        getAllAuctions()
    }

    private fun getAuction() {
        runBlocking {
            launch {
                AuctionSingleton.instance.getAuction().collect {
                    //_currentAuction.value = it
                }
            }
        }
    }

    private fun getAllAuctions() {
        runBlocking {
            launch {
                AuctionSingleton.instance.getAllAuctionsOld().collect {
                    _allAuctions.value = it
                }
            }
        }
    }

    fun refreshAuctions() {
        getAllAuctions()
    }

    fun updateCurrentAuction(auctionURI: String) {
        runBlocking {
            launch {
                LiveAuctionSingleton.instance.joinAuction(auctionURI)
                LiveAuctionSingleton.instance.getSpecificAuctionData().collect {
                    _currentAuction.value = it
                }
            }
        }
        print(_currentAuction.value.auctionImageURL)
        updateBid()
    }


    suspend fun createAuction(
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

    suspend fun bidOnAuction(userBid: String) {
        LiveAuctionSingleton.instance.sendBid(userBid)

    }

    fun deleteAuction() {

    }

    fun updateBid() {
            GlobalScope.launch(Dispatchers.IO) {
                        _currentAuction.value.copy(auctionHighestBid = LiveAuctionSingleton.instance.updateHighestBid())

                }
            }


    //private val _currentAuction: MutableStateFlow<SpecificAuctionData> = MutableStateFlow(specificDummyAuctionData)
    //val currentAuction: StateFlow<SpecificAuctionData> = _currentAuction

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

