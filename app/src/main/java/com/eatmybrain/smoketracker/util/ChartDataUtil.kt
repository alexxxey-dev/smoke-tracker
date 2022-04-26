package com.eatmybrain.smoketracker.util

import com.eatmybrain.smoketracker.data.structs.Session
import com.eatmybrain.smoketracker.ui.screens.statistics.enums.SessionsPeriod
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import java.util.concurrent.TimeUnit

object ChartDataUtil {

     fun parse(sessionList: List<Session>, currentPeriod: SessionsPeriod): LineDataSet {
        val entryList = entryList(currentPeriod)
        populateEntryList(entryList,sessionList)

        return LineDataSet(entryList, "")
    }



    private fun populateEntryList(
        entryList:List<Entry>,
        sessionList: List<Session>
    ){
        sessionList.forEach { session ->
            val dayStartTimestamp = Time.startOfDay(session.timestamp).toFloat()
            val yValue = (session.amount * session.amountType.weight).toFloat()
            entryList.find { it.x == dayStartTimestamp }?.let {
                it.y += yValue
            }
        }
    }

    private fun entryList(currentPeriod: SessionsPeriod):List<Entry>{
        val entryList = ArrayList<Entry>()
        val startTimestamp = currentPeriod.startTimestamp()

        repeat(currentPeriod.daysCount()){ index ->
            val dayIndex = (index + 1).toLong()
            val dayTimestamp = (startTimestamp + TimeUnit.DAYS.toMillis(dayIndex))
            val dayStartTimestamp = Time.startOfDay(dayTimestamp).toFloat()
           entryList.add(Entry(dayStartTimestamp, 0.01f))
        }
        return entryList
    }



}