package com.eatmybrain.smoketracker.data.data_store

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey

object BreakScheme {
     val IS_ACTIVE = booleanPreferencesKey("TOLERANCE_BREAK_ACTIVE")
     val DURATION = longPreferencesKey("TOLERANCE_BREAK_DURATION")
    val START_TIMESTAMP = longPreferencesKey("TOLERANCE_BREAK_START")
    val GRAM_PRICE = doublePreferencesKey("BREAK_GRAM_PRICE")
}