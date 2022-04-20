package com.eatmybrain.smoketracker.ui.screens.statistics.enums

import androidx.annotation.StringRes
import com.eatmybrain.smoketracker.R
import java.util.concurrent.TimeUnit

sealed class SessionsPeriod(
    @StringRes
    val textRes:Int
) {
    object Month : SessionsPeriod(R.string.per_month)
    object Week : SessionsPeriod(R.string.per_week)
    object Year : SessionsPeriod(R.string.per_year)

    companion object{
        fun values() = listOf(
            Week,
            Month,
            Year
        )
    }

    fun daysCount() : Int {
        return when(this) {
            Month -> 30
            Week -> 7
            Year -> 365
        }
    }
    fun startTimestamp() : Long{
        return when(this) {
            Month -> System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30)
            Week -> System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)
            Year -> System.currentTimeMillis() - TimeUnit.DAYS.toMillis(365)
        }
    }
}