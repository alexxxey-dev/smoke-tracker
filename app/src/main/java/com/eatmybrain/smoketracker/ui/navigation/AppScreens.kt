package com.eatmybrain.smoketracker.ui.navigation

import com.eatmybrain.smoketracker.R


const val ADD_SESSION_ROUTE = "add_session_route"
const val RESET_TOLERANCE_ROUTE = "reset_tolerance_route"
sealed class AppScreens(val title:String, val screenRoute:String){
    object AddSession : AppScreens("Add Session", ADD_SESSION_ROUTE)
    object ResetTolerance : AppScreens("Reset Tolerance", RESET_TOLERANCE_ROUTE)
}