package com.eatmybrain.smoketracker.data.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.eatmybrain.smoketracker.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyDataStore(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATA_STORE_NAME)

    suspend fun saveGramsPerSession(value:Double) {
        context.dataStore.edit {
            it[BreakScheme.GRAMS_PER_SESSION] = value
        }
    }

     fun getGramsPerSession():Flow<Double>{
        return context.dataStore.data.map {
            it[BreakScheme.GRAMS_PER_SESSION] ?: 0.0
        }
    }

    suspend fun saveSessionsPerWeek(value:Int){
        context.dataStore.edit {
            it[BreakScheme.SESSIONS_PER_WEEK] = value
        }
    }

    fun getSessionsPerWeek():Flow<Int>{
        return context.dataStore.data.map {
            it[BreakScheme.SESSIONS_PER_WEEK] ?: 0
        }
    }

    fun getBreakStart():Flow<Long>{
        return context.dataStore.data.map {
            it[BreakScheme.START_TIMESTAMP] ?: 0L
        }
    }

    suspend fun saveBreakStart(startTimestamp:Long){
        context.dataStore.edit {
            it[BreakScheme.START_TIMESTAMP] = startTimestamp
        }
    }

    suspend fun saveGramPrice(gramPrice:Double){
        context.dataStore.edit {
            it[BreakScheme.GRAM_PRICE] = gramPrice
        }
    }

    fun getGramPrice():Flow<Double>{
        return context.dataStore.data.map {
            it[BreakScheme.GRAM_PRICE] ?: 0.0
        }
    }
    fun getBreakDuration(): Flow<Long> {
        return context.dataStore.data.map {
            it[BreakScheme.DURATION] ?: 0L
        }
    }

    suspend fun saveBreakDuration(duration:Long){
        context.dataStore.edit { settings ->
            settings[BreakScheme.DURATION] = duration
        }
    }
    suspend fun toggleBreak() {
        context.dataStore.edit { settings ->
            settings[BreakScheme.IS_ACTIVE] =
                !(settings[BreakScheme.IS_ACTIVE] ?: false)
        }
    }

    fun isBreakActive(): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[BreakScheme.IS_ACTIVE] ?: false
        }
    }

}