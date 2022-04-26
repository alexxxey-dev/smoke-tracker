package com.eatmybrain.smoketracker.ui.navigation

import com.eatmybrain.smoketracker.R


const val STATISTICS_ROUTE = "statistics"
const val TOLERANCE_ROUTE = "tolerance"
const val STRAINS_ROUTE = "strain_search"
const val PREMIUM_ROUTE = "premium"
sealed class BottomNavItem(val title:String, val icon:Int, val screenRoute:String) {
    object Statistics : BottomNavItem("Statistics", R.drawable.ic_stats, STATISTICS_ROUTE)
    object Tolerance : BottomNavItem("Tolerance", R.drawable.ic_tolerance, TOLERANCE_ROUTE)
    object StrainSearch : BottomNavItem("Strains", R.drawable.ic_starins, STRAINS_ROUTE)
    object Premium : BottomNavItem("Premium", R.drawable.ic_star, PREMIUM_ROUTE)
}