package com.eatmybrain.smoketracker.util

import com.eatmybrain.smoketracker.data.structs.SessionsInfo
import java.util.concurrent.TimeUnit

object BreakTimeCalculator {
    fun calculate(sessionsInfo: SessionsInfo):Long{
        val smokeFreq = sessionsInfo.freq.toInt()
        val smokeAmount = sessionsInfo.amount.double()
        val price = sessionsInfo.price.double()

        return TimeUnit.DAYS.toMillis(1)
    }
}