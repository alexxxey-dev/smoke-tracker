package com.eatmybrain.smoketracker.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

@SuppressLint("ConstantLocale")
object Time {
    private val dayMonthYear = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    private val cal = Calendar.getInstance()

    fun dayMonthYear(timestamp:Long) :String{

        return dayMonthYear.format(Date(timestamp))
    }

    fun startOfDay(timestamp: Long): Long {
        val date = Date(timestamp)
        cal.apply {
            time = date
            set(Calendar.HOUR_OF_DAY,0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return cal.timeInMillis
    }



}