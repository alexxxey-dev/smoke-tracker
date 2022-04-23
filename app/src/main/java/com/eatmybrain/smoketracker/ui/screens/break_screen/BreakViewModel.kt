package com.eatmybrain.smoketracker.ui.screens.break_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eatmybrain.smoketracker.data.BreakRepository
import com.eatmybrain.smoketracker.util.BreakCalculator
import com.eatmybrain.smoketracker.util.formatZero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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


    init {
        loadTimeData()
        loadStatsData()
    }

    private fun loadTimeData() = viewModelScope.launch {
        val start = withContext(Dispatchers.IO) { breakRepository.getBreakStart() }
        val total = withContext(Dispatchers.IO) { breakRepository.getBreakDuration() }

        val endTime = start + total
        val currTime = System.currentTimeMillis()

        _leftTime.value = endTime - currTime
        _totalTime.value = total
    }


    private fun loadStatsData() = viewModelScope.launch {
        val startTime = withContext(Dispatchers.IO) { breakRepository.getBreakStart() }
        val pricePerGram = withContext(Dispatchers.IO) { breakRepository.getGramPrice() }
        val sessionsPerWeek = withContext(Dispatchers.IO) { breakRepository.getSessionsPerWeek() }
        val gramsPerSession = withContext(Dispatchers.IO) { breakRepository.getGramsPerSession() }

        val moneySaved = BreakCalculator
                .moneySaved(sessionsPerWeek, pricePerGram, gramsPerSession, startTime)
                .formatZero()
        _moneySaved.value = "${moneySaved}$"

        val gramsAvoided =  BreakCalculator
            .gramsAvoided(sessionsPerWeek, gramsPerSession, startTime)
            .formatZero()
        _gramsAvoided.value = "${gramsAvoided}g"

        _weedFreeTime.value = BreakCalculator.passedBreakTime(startTime)
    }


    fun toggleBreak() = viewModelScope.launch {
        withContext(Dispatchers.IO) { breakRepository.toggleBreak() }
    }
}