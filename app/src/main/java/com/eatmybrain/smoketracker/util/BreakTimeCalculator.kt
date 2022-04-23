package com.eatmybrain.smoketracker.util

import com.eatmybrain.smoketracker.data.structs.SessionsInfo
import java.util.concurrent.TimeUnit

object BreakTimeCalculator {


    fun calculate(sessionsInfo: SessionsInfo):Long{
        val sessionsPerWeek = sessionsInfo.freq.toInt()
        val smokeAmount = sessionsInfo.amount.double()

        val week = TimeUnit.DAYS.toMillis(7)


        return when(sessionsPerWeek){
            in 99.downTo(21) -> if(smokeAmount > 0.5) week * 7  else week * 6
            in 21.downTo(14) -> if(smokeAmount > 0.5) week * 6  else week * 5
            in 14.downTo(7) -> if(smokeAmount > 0.5) week * 5  else week * 4
            in 7.downTo(5) ->if(smokeAmount > 0.5) week * 4  else week * 2
            in 5.downTo(3) ->  if(smokeAmount > 0.5) week * 3 else week * 2
            else -> week * 2
        }
    }
}