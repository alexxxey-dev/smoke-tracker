package com.eatmybrain.smoketracker.data.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.eatmybrain.smoketracker.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyDataStore(private val context:Context) {
    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = Constants.DATA_STORE_NAME)

    suspend fun saveSmokeFreq(smokeFreq:Int) {
        context.dataStore.edit {settings->
            settings[SmokeInfoScheme.SMOKE_FREQ] = smokeFreq
        }
    }

     fun getSmokeFreq(): Flow<Int> {
        return context.dataStore.data.map { prefs->
            prefs[SmokeInfoScheme.SMOKE_FREQ] ?: 0
        }
    }


    suspend fun saveSmokeAmount(smokeAmount:Double) {
        context.dataStore.edit { settings->
            settings[SmokeInfoScheme.SMOKE_AMOUNT] = smokeAmount
        }
    }

    fun getSmokeAmount():Flow<Double>{
        return context.dataStore.data.map { prefs->
            prefs[SmokeInfoScheme.SMOKE_AMOUNT] ?: 0.0
        }
    }


    suspend fun savePrice(price:Double) {
        context.dataStore.edit { settings->
            settings[SmokeInfoScheme.AVERAGE_PRICE] = price
        }
    }

    fun getPrice():Flow<Double> {
        return context.dataStore.data.map {
            it[SmokeInfoScheme.AVERAGE_PRICE] ?: 0.0
        }
    }
}