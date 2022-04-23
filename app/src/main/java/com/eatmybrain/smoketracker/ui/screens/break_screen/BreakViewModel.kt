package com.eatmybrain.smoketracker.ui.screens.break_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eatmybrain.smoketracker.data.BreakRepository
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
    val leftTime:LiveData<Long> = _leftTime

    private val _totalTime = MutableLiveData<Long>()
    val totalTime:LiveData<Long> = _totalTime

    init {
        loadTimeData()
    }

    private fun loadTimeData() = viewModelScope.launch{
        val start = withContext(Dispatchers.IO){breakRepository.getBreakStart()}
        val total = withContext(Dispatchers.IO){breakRepository.getBreakDuration()}

        val endTime = start + total
        val currTime = System.currentTimeMillis()

        _leftTime.value = endTime - currTime
        _totalTime.value = total
    }

    fun toggleBreak() = viewModelScope.launch {
        withContext(Dispatchers.IO) { breakRepository.toggleBreak() }
    }
}