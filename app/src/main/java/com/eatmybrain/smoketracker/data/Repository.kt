package com.eatmybrain.smoketracker.data


import  com.eatmybrain.smoketracker.data.api.StrainsApi
import com.eatmybrain.smoketracker.data.db.AppDatabase
import com.eatmybrain.smoketracker.data.structs.Session
import com.eatmybrain.smoketracker.data.structs.StrainInfo
import com.eatmybrain.smoketracker.ui.screens.statistics.enums.SessionsPeriod

class Repository(
    private val db: AppDatabase,
    private val strainsApi:StrainsApi
) {
    suspend fun addSession(session:Session) {
        db.sessionsDao().insert(session)
    }
    suspend fun strainInfo(strainName:String) : StrainInfo? {
        return null
    }

    suspend fun sessionHistory(sessionPeriod: SessionsPeriod): List<Session> {
        val start = sessionPeriod.startTimestamp()
        return db.sessionsDao().sortByPeriod(start)
    }
}