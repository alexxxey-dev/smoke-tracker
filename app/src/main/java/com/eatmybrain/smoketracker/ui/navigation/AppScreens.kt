package com.eatmybrain.smoketracker.ui.navigation

import com.eatmybrain.smoketracker.R


const val ADD_SESSION_ROUTE = "add_session_route"
sealed class AppScreens(val title:String, val screenRoute:String){
    object AddSession : AppScreens("Add Session", ADD_SESSION_ROUTE)
}