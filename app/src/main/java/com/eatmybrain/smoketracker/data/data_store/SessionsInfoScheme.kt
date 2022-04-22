package com.eatmybrain.smoketracker.data.data_store

import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object SessionsInfoScheme {
    val SMOKE_FREQ = intPreferencesKey("SMOKE_FREQ")
    val SMOKE_AMOUNT = doublePreferencesKey("SMOKE_AMOUNT")
    val AVERAGE_PRICE = doublePreferencesKey("AVERAGE_PRICE")
}