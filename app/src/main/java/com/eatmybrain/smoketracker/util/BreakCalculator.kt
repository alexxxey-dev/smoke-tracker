package com.eatmybrain.smoketracker.util

import com.eatmybrain.smoketracker.data.structs.SessionsInfo
import java.util.concurrent.TimeUnit

object BreakCalculator {
    private val weekDaysCount = 7L

    fun breakTime(sessionsInfo: SessionsInfo): Long {
        val sessionsPerWeek = sessionsInfo.freq.toInt()
        val smokeAmount = sessionsInfo.amount.double()

        val week = TimeUnit.DAYS.toMillis(weekDaysCount)


        return when (sessionsPerWeek) {
            in 99.downTo(21) -> if (smokeAmount > 0.5) week * 7 else week * 6
            in 21.downTo(14) -> if (smokeAmount > 0.5) week * 6 else week * 5
            in 14.downTo(7) -> if (smokeAmount > 0.5) week * 5 else week * 4
            in 7.downTo(5) -> if (smokeAmount > 0.5) week * 4 else week * 2
            in 5.downTo(3) -> if (smokeAmount > 0.5) week * 3 else week * 2
            else -> week * 2
        }
    }


    fun moneySaved(
        sessionsPerWeek: Int,
        gramPrice: Double,
        gramsPerSession: Double,
        breakStart: Long
    ): Double {
        return gramsAvoided(sessionsPerWeek, gramsPerSession, breakStart) * gramPrice
    }


    fun passedBreakTime(breakStart: Long): String {
        val passedTime = System.currentTimeMillis() - breakStart
        val days = TimeUnit.MILLISECONDS.toDays(passedTime)
        val hours = TimeUnit.MILLISECONDS.toHours(passedTime)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(passedTime)
        return  if(days>0) "$days day(s)" else if(hours>0) "$hours hour(s)" else "$minutes minute(s)"
    }

    fun gramsAvoided(
        sessionsPerWeek: Int,
        gramsPerSession: Double,
        breakStart: Long
    ): Double {
        val passedTime = System.currentTimeMillis() - breakStart
        val days = TimeUnit.MILLISECONDS.toDays(passedTime)
        val sessionsPerDay = sessionsPerWeek.div(weekDaysCount.toDouble())
        return days * sessionsPerDay * gramsPerSession
    }
}