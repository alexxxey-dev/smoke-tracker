package com.eatmybrain.smoketracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.eatmybrain.smoketracker.ui.navigation.BottomNav
import com.eatmybrain.smoketracker.ui.navigation.BottomNavItem
import com.eatmybrain.smoketracker.ui.navigation.NavigationGraph
import com.eatmybrain.smoketracker.ui.screens.add_session.AddSessionViewModel
import com.eatmybrain.smoketracker.ui.theme.SmokeTrackerTheme
import com.eatmybrain.smoketracker.util.currentRoute
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmokeTrackerTheme {
                AppScreen()
            }
        }
    }


    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun addSessionViewModelFactory() : AddSessionViewModel.Factory
    }
    @Composable
    fun AppScreen() {
        val navController = rememberNavController()
        val currentRoute = currentRoute(navController)
        Scaffold(bottomBar = {
            if (currentRoute == BottomNavItem.Statistics.screenRoute
                || currentRoute == BottomNavItem.StrainSearch.screenRoute
                || currentRoute == BottomNavItem.Tolerance.screenRoute
                || currentRoute == BottomNavItem.Premium.screenRoute) {
                BottomNav(navController = navController)
            }
        }) {
            NavigationGraph(navController = navController)
        }
    }


}



