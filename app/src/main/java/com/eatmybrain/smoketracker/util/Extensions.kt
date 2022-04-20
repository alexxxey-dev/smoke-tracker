package com.eatmybrain.smoketracker.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

@Composable
fun currentRoute(navController: NavController):String?{
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}






fun Double.formatZero() = (if (this.toInt().toDouble() == this) {
    DecimalFormat("#.##").apply {
        decimalFormatSymbols = DecimalFormatSymbols.getInstance().apply {
            decimalSeparator = '.'
        }
    }
} else {
    DecimalFormat("#.00").apply {
        decimalFormatSymbols = DecimalFormatSymbols.getInstance().apply {
            decimalSeparator = '.'
        }
    }
}).format(this)