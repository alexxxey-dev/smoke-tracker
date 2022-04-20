package com.eatmybrain.smoketracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eatmybrain.smoketracker.ui.screens.add_session.AddSessionScreen
import com.eatmybrain.smoketracker.ui.screens.premium.PremiumScreen
import com.eatmybrain.smoketracker.ui.screens.statistics.StatisticsScreen
import com.eatmybrain.smoketracker.ui.screens.strain_search.StrainSearchScreen
import com.eatmybrain.smoketracker.ui.screens.tolerance.ToleranceScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Statistics.screenRoute
    ) {
        composable(BottomNavItem.Statistics.screenRoute) {
            StatisticsScreen(
                onAddSession = {
                    navController.navigate(AppScreens.AddSession.screenRoute)
                },
                onSessionClicked = {
                    //TODO
                }
            )
        }

        composable(BottomNavItem.Tolerance.screenRoute) {
            ToleranceScreen()
        }

        composable(BottomNavItem.StrainSearch.screenRoute) {
            StrainSearchScreen()
        }

        composable(BottomNavItem.Premium.screenRoute) {
            PremiumScreen()
        }

        composable(AppScreens.AddSession.screenRoute) {
            AddSessionScreen(
                navigateToStrainInfo = {
                    //TODO
                },
                navigateToHighTest = {
                    //TODO
                },
                navigateHome = {
                    navController.navigate(BottomNavItem.Statistics.screenRoute)
                }
            )
        }
    }
}