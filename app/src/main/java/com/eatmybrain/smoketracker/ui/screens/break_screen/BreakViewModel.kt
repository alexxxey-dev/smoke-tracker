package com.eatmybrain.smoketracker.ui.screens.break_screen

import androidx.lifecycle.*
import com.eatmybrain.smoketracker.data.BreakRepository
import com.eatmybrain.smoketracker.util.BreakUtil
import com.eatmybrain.smoketracker.util.formatZero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject


@HiltViewModel
class BreakViewModel @Inject constructor(
    private val breakRepository: BreakRepository
) : ViewModel() {
    private val _leftTime = MutableLiveData<Long>()
    val leftTime: LiveData<Long> = _leftTime

    private val _totalTime = MutableLiveData<Long>()
    val totalTime: LiveData<Long> = _totalTime

    private val _moneySaved = MutableLiveData<String>()
    val moneySaved: LiveData<String> = _moneySaved

    private val _weedFreeTime = MutableLiveData<String>()
    val weedFreeTime: LiveData<String> = _weedFreeTime

    private val _gramsAvoided = MutableLiveData<String>()
    val gramsAvoided: LiveData<String> = _gramsAvoided

    val isBreakActive = breakRepository.isBreakActive().asLiveData()

    private var timer: Timer? = null
    private val ONE_SECOND = 1000L

    fun init() = viewModelScope.launch {
        loadTotalTime()
        startTimer()
        loadStatistics()
    }


    private suspend fun loadTotalTime() {
        _totalTime.value = withContext(Dispatchers.IO) { breakRepository.getBreakDuration() }
    }


    private suspend fun startTimer()  {
        val endTime = withContext(Dispatchers.IO) {
            breakRepository.getBreakStart() + breakRepository.getBreakDuration()
        }

        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                updateTime(endTime)
                updateWeedFreeTime()
            }
        }, ONE_SECOND, ONE_SECOND)

    }
    private suspend fun loadStatistics() {
        val moneySaved = withContext(Dispatchers.IO) { breakRepository.moneySaved().formatZero() }
        _moneySaved.value = "$moneySaved $"
        val gramsAvoided =  withContext(Dispatchers.IO) { breakRepository.gramsAvoided().formatZero() }
        _gramsAvoided.value = "$gramsAvoided g"
    }


    private fun updateTime(endTime:Long) = viewModelScope.launch {
        val currentTime = System.currentTimeMillis()
        val newValue = endTime - currentTime
        _leftTime.postValue(newValue)
    }

    private fun updateWeedFreeTime()  = viewModelScope.launch{
        _weedFreeTime.value = withContext(Dispatchers.IO) {
            val time = breakRepository.weedFreeTime()
            BreakUtil.passedTimeString(time)
        }
    }



    fun stopBreak() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            breakRepository.toggleBreak(false)
            breakRepository.clear()
        }
    }


    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}