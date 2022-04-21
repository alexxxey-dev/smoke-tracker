package com.eatmybrain.smoketracker.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.eatmybrain.smoketracker.data.structs.Session


@Dao
interface SessionsDao {
    @Insert
    suspend fun insert(session: Session)

    @Update
    suspend fun update(session: Session)

    @Query("SELECT * FROM session_list WHERE timestamp == :timestamp")
    suspend fun byTimestamp(timestamp:Long) :Session?

    @Query("SELECT EXISTS(SELECT * FROM session_list WHERE timestamp = :id)")
    suspend fun exists(id: Long) :Boolean

    @Query("SELECT * FROM session_list WHERE timestamp >= :start")
    suspend fun sortByPeriod(start:Long) : List<Session>
}