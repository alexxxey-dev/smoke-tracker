package com.eatmybrain.smoketracker.data

import com.eatmybrain.smoketracker.data.data_store.MyDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class BreakRepository(
    private val myDataStore: MyDataStore
) {

    suspend fun clear(){
        saveSessionsPerWeek(0)
        saveGramsPerSession(0.0)
        saveGramPrice(0.0)
        saveBreakDuration(0)
        saveBreakStart(0)
    }
    suspend fun toggleBreak(){
        myDataStore.toggleBreak()
    }

    fun isBreakActive(): Flow<Boolean> {
        return  myDataStore.isBreakActive()
    }
    suspend fun saveSessionsPerWeek(value:Int) = myDataStore.saveSessionsPerWeek(value)

    suspend fun getSessionsPerWeek():Int = myDataStore.getSessionsPerWeek().first()

    suspend fun saveGramsPerSession(value:Double) = myDataStore.saveGramsPerSession(value)

    suspend fun getGramsPerSession():Double = myDataStore.getGramsPerSession().first()

    suspend fun saveGramPrice(price:Double) = myDataStore.saveGramPrice(price)

    suspend fun getGramPrice() = myDataStore.getGramPrice().first()

    suspend fun getBreakDuration() = myDataStore.getBreakDuration().first()

    suspend fun getBreakStart() = myDataStore.getBreakStart().first()

    suspend fun saveBreakDuration(duration:Long) = myDataStore.saveBreakDuration(duration)

    suspend fun saveBreakStart(startTimestamp:Long) = myDataStore.saveBreakStart(startTimestamp)
}