package com.eatmybrain.smoketracker.ui.screens.statistics

import androidx.lifecycle.*
import com.eatmybrain.smoketracker.data.SessionsRepository
import com.eatmybrain.smoketracker.data.structs.Session
import com.eatmybrain.smoketracker.ui.screens.statistics.enums.SessionsPeriod
import com.eatmybrain.smoketracker.util.ChartDataUtil
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val sessionsRepository: SessionsRepository
) : ViewModel() {

    private val _sessionsPeriod = MutableLiveData<SessionsPeriod>()
    val sessionsPeriod: LiveData<SessionsPeriod> = _sessionsPeriod

    val sessionList = Transformations.switchMap(sessionsPeriod) { period ->
        sessionsHistory(period)
    }

    val sessionsLineData = Transformations.switchMap(sessionList){ sessions->
        val sessionsPeriod = _sessionsPeriod.value!!
        sessionsLineData(sessions, sessionsPeriod)
    }

    val moneySpent = Transformations.switchMap(sessionList){sessions->
         moneySpent(sessions)
    }

    init {
        updateSessionsPeriod(SessionsPeriod.Week)
    }

    fun updateSessionsPeriod(period: SessionsPeriod) {
        _sessionsPeriod.value = period
    }

    private fun sessionsLineData(sessions:List<Session>, sessionsPeriod: SessionsPeriod) :LiveData<LineDataSet>{
        val lineData = MutableLiveData<LineDataSet>()
        viewModelScope.launch {
            lineData.value = withContext(Dispatchers.IO){
                ChartDataUtil.parse(sessions, sessionsPeriod)
            }
        }
        return lineData
    }

    private fun sessionsHistory(sessionsPeriod: SessionsPeriod): LiveData<List<Session>> {
        val history = MutableLiveData<List<Session>>()
        viewModelScope.launch {
            history.value = withContext(Dispatchers.IO) {
                sessionsRepository.sessionHistory(sessionsPeriod).sortedByDescending { it.timestamp }
            }
        }
        return history
    }

    private fun moneySpent(sessions:List<Session>):LiveData<Double>{
        val moneySpent = MutableLiveData<Double>()
        viewModelScope.launch {
            moneySpent.value = withContext(Dispatchers.IO){
                sessions.sumOf { it.amount * it.amountType.weight * it.pricePerGram}
            }
        }
        return moneySpent
    }
}