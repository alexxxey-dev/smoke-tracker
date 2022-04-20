package com.eatmybrain.smoketracker.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.eatmybrain.smoketracker.data.structs.Session


@Dao
interface SessionsDao {
    @Insert
    suspend fun insert(session: Session)


    @Query("SELECT * FROM session_list WHERE timestamp >= :start")
    suspend fun sortByPeriod(start:Long) : List<Session>
}