package com.eatmybrain.smoketracker.util

import com.eatmybrain.smoketracker.data.data_store.MyDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class PremiumUtil(
    private val dataStore: MyDataStore
) {
    suspend fun activate(){
        val hasPremium = check().first()
        if(!hasPremium){
            dataStore.togglePremium()
        }
    }

    suspend fun deactivate(){
        val hasPremium = check().first()
        if(hasPremium){
            dataStore.togglePremium()
        }
    }

    fun check() : Flow<Boolean> {
        return dataStore.hasPremium()
    }
}