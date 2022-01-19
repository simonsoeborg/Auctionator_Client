package controller

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import model.SpecificAuctionData
import java.text.SimpleDateFormat
import java.util.*

// TODO TimerController skal kun instantieres ved OnCreate Auction

class TimerController(currentAuction : StateFlow<SpecificAuctionData>) {
    private val timerScope = CoroutineScope(Dispatchers.IO) // Specific Scope for Timer only
    
    private lateinit var currentAuction : StateFlow<SpecificAuctionData>
    init {
        this.currentAuction = currentAuction
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