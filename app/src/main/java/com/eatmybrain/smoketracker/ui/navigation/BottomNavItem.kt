package com.eatmybrain.smoketracker.ui.navigation

import com.eatmybrain.smoketracker.R


const val STATISTICS_ROUTE = "statistics"
const val TOLERANCE_ADVICE_ROUTE = "tolerance"
const val STRAINS_ROUTE = "strain_search"
const val PREMIUM_ROUTE = "premium"
const val TOLERANCE_BREAK_ROUTE = "tolerance break route"
sealed class BottomNavItem(val title:String, val icon:Int, val screenRoute:String) {
    object Statistics : BottomNavItem("Statistics", R.drawable.ic_stats, STATISTICS_ROUTE)
    object ToleranceAdvice : BottomNavItem("Tolerance", R.drawable.ic_tolerance, TOLERANCE_ADVICE_ROUTE)
    object ToleranceBreak : BottomNavItem("Tolerance", R.drawable.ic_tolerance, TOLERANCE_BREAK_ROUTE)
    object StrainSearch : BottomNavItem("Strains", R.drawable.ic_starins, STRAINS_ROUTE)
    object Premium : BottomNavItem("Premium", R.drawable.ic_star, PREMIUM_ROUTE)

    companion object{
        fun BottomNavItem.isVisible( hasPremium: Boolean?, breakActive: Boolean?) =  when (this) {
            Premium -> hasPremium != true
            ToleranceAdvice -> breakActive == false
            ToleranceBreak -> breakActive == true
            else -> true
        }

        fun values() = listOf(
            Statistics,
            StrainSearch,
           ToleranceAdvice,
            ToleranceBreak,
                    Premium
        )
    }
}