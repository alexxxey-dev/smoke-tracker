package com.eatmybrain.smoketracker.util

import com.eatmybrain.smoketracker.data.structs.SessionsInfo
import java.util.concurrent.TimeUnit

object BreakUtil {
    private val weekDaysCount = 7L

    fun calculateBreakTime(sessionsInfo: SessionsInfo): Long {
        val sessionsPerWeek = sessionsInfo.freq.toInt()
        val smokeAmount = sessionsInfo.amount.double()

        val week = TimeUnit.DAYS.toMillis(weekDaysCount)


        return when (sessionsPerWeek) {
            in 99.downTo(21) -> if (smokeAmount > 0.5) week * 12 else week * 11
            in 21.downTo(14) -> if (smokeAmount > 0.5) week * 10 else week * 9
            in 14.downTo(7) -> if (smokeAmount > 0.5) week * 8 else week * 7
            in 7.downTo(5) -> if (smokeAmount > 0.5) week * 6 else week * 5
            in 5.downTo(3) -> if (smokeAmount > 0.5) week * 4 else week * 3
            else -> week * 2
        }
    }


    fun calculateMoneySaved(
        sessionsPerWeek: Int,
        gramPrice: Double,
        gramsPerSession: Double,
        breakStart: Long
    ): Double {
        val result = calculateGramsAvoided(sessionsPerWeek, gramsPerSession, breakStart) * gramPrice
        return result.round(2)
    }


    fun passedTimeString(passedTime: Long): String {
        val days = TimeUnit.MILLISECONDS.toDays(passedTime)
        val hours = TimeUnit.MILLISECONDS.toHours(passedTime)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(passedTime)
        return  if(days>0) "$days day(s)" else if(hours>0) "$hours hour(s)" else "${minutes + 1} min(s)"
    }

    fun calculateGramsAvoided(
        sessionsPerWeek: Int,
        gramsPerSession: Double,
        breakStart: Long
    ): Double {
        val passedTime = System.currentTimeMillis() - breakStart
        val days = TimeUnit.MILLISECONDS.toDays(passedTime)
        val sessionsPerDay = sessionsPerWeek.div(weekDaysCount.toDouble())
        val result = days * sessionsPerDay * gramsPerSession
        return result.round(2)
    }
}