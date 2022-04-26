package com.eatmybrain.smoketracker.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.round

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}


fun String.removeCommas() = this.replace(",", "").replace(".", "")

fun String.countCommas(): Int {
    var counter = 0
    this.forEach {
        if (it == '.' || it == ',') counter++
    }
    return counter
}

fun String.double() = this.replace(",", ".").toDouble()

fun Double.formatZero(): String {
    val format =  DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).apply {
        maximumFractionDigits = 5
    }
    return format.format(this)
}


fun Double.round(decimals: Int): Double {
    var multiplier = 1.0f
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}