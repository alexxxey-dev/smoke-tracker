package com.eatmybrain.smoketracker.ui.navigation

import com.eatmybrain.smoketracker.R


const val STATISTICS_ROUTE = "statistics"
const val TOLERANCE_ROUTE = "tolerance"
const val STRAINS_ROUTE = "strain_search"
const val PREMIUM_ROUTE = "premium"
sealed class BottomNavItem(val title:String, val icon:Int, val screenRoute:String) {
    object Statistics : BottomNavItem("Statistics", R.drawable.bottom_nav_statistics, STATISTICS_ROUTE)
    object Tolerance : BottomNavItem("Tolerance", R.drawable.bottom_nav_tolerance, TOLERANCE_ROUTE)
    object StrainSearch : BottomNavItem("Strains", R.drawable.bottom_nav_strains, STRAINS_ROUTE)
    object Premium : BottomNavItem("Premium", R.drawable.bottom_nav_premium, PREMIUM_ROUTE)
}