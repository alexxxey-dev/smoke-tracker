package com.eatmybrain.smoketracker.util

import com.eatmybrain.smoketracker.data.structs.Session
import com.eatmybrain.smoketracker.ui.screens.statistics.enums.SessionsPeriod
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import java.util.concurrent.TimeUnit

class ChartDataParser {

    fun parse(sessionList: List<Session>, currentPeriod: SessionsPeriod): LineDataSet {
        val daysList = ArrayList<Entry>()

        repeat(currentPeriod.daysCount()){ dayIndex ->
            daysList.add(Entry(dayIndex.toFloat(), 0.01f))
        }

        sessionList.forEach { session ->
            val diff = session.timestamp - currentPeriod.startTimestamp()
            val daysCount = currentPeriod.daysCount() - 1
            val sessionDayIndex =  daysCount - TimeUnit.MILLISECONDS.toDays(diff).toInt()
            val yValue = (session.amount * session.amountType.weight).toFloat()
            daysList[sessionDayIndex].y += yValue
        }


        return LineDataSet(daysList, "")
    }
}