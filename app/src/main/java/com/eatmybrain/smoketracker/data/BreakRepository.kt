package com.eatmybrain.smoketracker.data

import com.eatmybrain.smoketracker.data.data_store.MyDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class BreakRepository(
    private val myDataStore: MyDataStore
) {

    suspend fun toggleBreak(){
        myDataStore.toggleBreak()
    }

    fun isBreakActive(): Flow<Boolean> {
        return  myDataStore.isBreakActive()
    }

    suspend fun getBreakDuration() = myDataStore.getBreakDuration().first()

    suspend fun getBreakStart() = myDataStore.getBreakStart().first()

    suspend fun saveBreakDuration(duration:Long) = myDataStore.saveBreakDuration(duration)

    suspend fun saveBreakStart(startTimestamp:Long) = myDataStore.saveBreakStart(startTimestamp)
}