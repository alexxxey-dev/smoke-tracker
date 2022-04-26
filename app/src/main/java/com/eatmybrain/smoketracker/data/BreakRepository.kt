package com.eatmybrain.smoketracker.data

import com.eatmybrain.smoketracker.data.data_store.MyDataStore
import com.eatmybrain.smoketracker.data.structs.Achievement
import com.eatmybrain.smoketracker.util.BreakUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class BreakRepository(
    private val dataStore: MyDataStore,
    private val achievements: AchievementsProvider
) {
    suspend fun achievements():List<Achievement>{
        return achievements.provide(
            moneySaved = moneySaved(),
            gramsAvoided = gramsAvoided(),
            weedFreeTime = weedFreeTime()
        )
    }

    suspend fun weedFreeTime():Long{
        val breakStart = getBreakStart()
        return  System.currentTimeMillis() - breakStart
    }
    suspend fun gramsAvoided():Double{
        val startTime = getBreakStart()
        val sessionsPerWeek = getSessionsPerWeek()
        val gramsPerSession = getGramsPerSession()
        return BreakUtil.calculateGramsAvoided(sessionsPerWeek, gramsPerSession, startTime)
    }

    suspend fun moneySaved() : Double{
        val startTime = getBreakStart()
        val pricePerGram = getGramPrice()
        val sessionsPerWeek = getSessionsPerWeek()
        val gramsPerSession = getGramsPerSession()

        return BreakUtil.calculateMoneySaved(sessionsPerWeek, pricePerGram, gramsPerSession, startTime)
    }
    suspend fun clear(){
        saveSessionsPerWeek(0)
        saveGramsPerSession(0.0)
        saveGramPrice(0.0)
        saveBreakDuration(0)
        saveBreakStart(0)
    }
    suspend fun toggleBreak(){
        dataStore.toggleBreak()
    }

    fun isBreakActive(): Flow<Boolean> {
        return  dataStore.isBreakActive()
    }
    suspend fun saveSessionsPerWeek(value:Int) = dataStore.saveSessionsPerWeek(value)

    suspend fun getSessionsPerWeek():Int = dataStore.getSessionsPerWeek().first()

    suspend fun saveGramsPerSession(value:Double) = dataStore.saveGramsPerSession(value)

    suspend fun getGramsPerSession():Double = dataStore.getGramsPerSession().first()

    suspend fun saveGramPrice(price:Double) = dataStore.saveGramPrice(price)

    suspend fun getGramPrice() = dataStore.getGramPrice().first()

    suspend fun getBreakDuration() = dataStore.getBreakDuration().first()

    suspend fun getBreakStart() = dataStore.getBreakStart().first()

    suspend fun saveBreakDuration(duration:Long) = dataStore.saveBreakDuration(duration)

    suspend fun saveBreakStart(startTimestamp:Long) = dataStore.saveBreakStart(startTimestamp)
}