package com.eatmybrain.smoketracker.data


import  com.eatmybrain.smoketracker.data.api.StrainsApi
import com.eatmybrain.smoketracker.data.data_store.MyDataStore
import com.eatmybrain.smoketracker.data.db.AppDatabase
import com.eatmybrain.smoketracker.data.structs.Session
import com.eatmybrain.smoketracker.data.structs.StrainInfo
import com.eatmybrain.smoketracker.ui.screens.statistics.enums.SessionsPeriod
import kotlinx.coroutines.flow.Flow

class Repository(
    private val db: AppDatabase,
    private val strainsApi: StrainsApi,
    private val myDataStore: MyDataStore
) {
    suspend fun toggleToleranceBreak(){
        myDataStore.toggleToleranceBreak()
    }

    fun isToleranceBreakActive(): Flow<Boolean> {
       return  myDataStore.isToleranceBreakActive()
    }
    suspend fun addSession(session: Session) {
        if (db.sessionsDao().exists(session.timestamp)) {
            db.sessionsDao().update(session)
        } else {
            db.sessionsDao().insert(session)
        }

    }

    suspend fun session(timestamp: Long): Session? {
        return db.sessionsDao().byTimestamp(timestamp)
    }

    suspend fun strainInfo(strainName: String): StrainInfo {
        return searchStrainInfo(strainName) ?: StrainInfo(
            imageUrl = "",
            title = strainName
        )
    }

    suspend fun searchStrainInfo(strainName: String): StrainInfo? {
        return null
    }

    suspend fun saveSmokeData(
        smokeFreq: Int,
        smokeAmount: Double,
        price: Double
    ) {
        myDataStore.apply {
            savePrice(price)
            saveSmokeAmount(smokeAmount)
            saveSmokeFreq(smokeFreq)
        }
    }

    suspend fun sessionHistory(sessionPeriod: SessionsPeriod): List<Session> {
        val start = sessionPeriod.startTimestamp()
        return db.sessionsDao().sortByPeriod(start)
    }
}