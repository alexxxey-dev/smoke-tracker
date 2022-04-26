package com.eatmybrain.smoketracker.ui.navigation

import com.eatmybrain.smoketracker.R


const val ADD_SESSION_ROUTE = "add_session_route"
const val ACHIEVEMENTS_ROUTE = "achievements_route"
sealed class AppScreens(val title:String, val screenRoute:String){
    object AddSession : AppScreens("Add Session", ADD_SESSION_ROUTE)
    object Achievements : AppScreens("Achievements", ACHIEVEMENTS_ROUTE)
}