package com.eatmybrain.smoketracker.util

import java.text.SimpleDateFormat
import java.util.*

object Time {

    fun ddMMyyy(timestamp:Long) :String{
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}