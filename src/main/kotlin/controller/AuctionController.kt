package controller

import factories.LoginItems
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import model.SpecificAuctionData
import repository.AuctionRepository_impl
import java.time.LocalTime
import javax.swing.plaf.nimbus.State

class AuctionController {
    private val auctionScope = CoroutineScope(Dispatchers.IO) // Specific Scope for Auctions only
    private val auctionRepo = AuctionRepository_impl()
    var auctionID = "null"

    private val specificDummyAuctionData: SpecificAuctionData = SpecificAuctionData(
        "null",
        "Test",
        "500",
        "550",
        "255",
        "Test test",
        "https://hvidevareshoppen.dk/images/BraunKF3120Hvid.jpg",
        LoginItems.userName
    )

    private val _currentAuction: MutableStateFlow<SpecificAuctionData> = MutableStateFlow(specificDummyAuctionData)
    val currentAuction: StateFlow<SpecificAuctionData> = _currentAuction

    private val _onlineBidders: MutableStateFlow<Int> = MutableStateFlow(0)
    val onlineBidders: StateFlow<Int> = _onlineBidders

    private val _timeRemaining: MutableStateFlow<Int> = MutableStateFlow(0)
    val timeRemaining: StateFlow<Int> = _timeRemaining

    init {
        listenForOnlineBidders()
    }

    fun joinAuction() {
        _currentAuction.value = auctionRepo.getSpecificAuctionData(auctionID)
        userOnline()
        println(_currentAuction.value.auctionTimeRemaining)
        _timeRemaining.value = convertTimestampToSeconds(_currentAuction.value.auctionTimeRemaining)
    }

    fun convertTimestampToSeconds(timestamp: String): Int{
        val temp = timestamp.split(":")
        val hours = temp[0].toInt()
        val minutes = temp[1].toInt()
        val seconds = temp[2].toInt()
        val currentTime = LocalTime.now()
        val hours2 = currentTime.hour
        val minutes2 = currentTime.minute
        val seconds2 = currentTime.second
        val timeRemaining = ((hours-hours2)*60*60+(minutes-minutes2)*60+seconds-seconds2)
        println("auction time remaining: "+ timeRemaining)
        return timeRemaining
    }

    fun userOnline(): Boolean {
        return auctionRepo.userOnline(auctionID)
    }

    fun leaveAuction(){
        auctionRepo.leaveAuction(auctionID, LoginItems.userName)
        _onlineBidders.value = 0
    }

    fun listenForOnlineBidders(){
        auctionScope.launch {
            _onlineBidders.value = auctionRepo.getOnlineClients().toInt()
        }
    }

    fun listenForNewAuctionData(){
        auctionScope.launch {
            while (true) {
                withTimeout(1500L){
                    try {
                        _currentAuction.value = auctionRepo.updateSpecificAuctionData(auctionID, LoginItems.userName)
                        _onlineBidders.value = auctionRepo.getOnlineClients().toInt()
                    } catch (t : TimeoutCancellationException) {
                        println(t)
                    }
                }
                delay(1000L)
            }
        }
    }

    fun bidOnAuction(userBid: String) {
        auctionRepo.sendBid(userBid, auctionID)
    }

}
