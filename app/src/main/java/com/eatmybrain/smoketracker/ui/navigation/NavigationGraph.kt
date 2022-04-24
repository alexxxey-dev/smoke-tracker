package com.eatmybrain.smoketracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.eatmybrain.smoketracker.ui.screens.add_session.AddSessionScreen
import com.eatmybrain.smoketracker.ui.screens.premium.PremiumScreen
import com.eatmybrain.smoketracker.ui.screens.statistics.StatisticsScreen
import com.eatmybrain.smoketracker.ui.screens.strain_search.StrainSearchScreen
import com.eatmybrain.smoketracker.ui.screens.tolerance_advice.ToleranceAdviceScreen
import com.eatmybrain.smoketracker.ui.screens.break_screen.BreakScreen
import com.eatmybrain.smoketracker.util.Constants

@Composable
fun NavigationGraph(navController: NavHostController, breakActive:Boolean) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Statistics.screenRoute
    ) {
        composable(BottomNavItem.Statistics.screenRoute) {
            StatisticsScreen(
                onAddSession = {
                    navController.navigate("${AppScreens.AddSession.screenRoute}/${0L}")
                },
                onSessionClicked = {
                    navController.navigate("${AppScreens.AddSession.screenRoute}/${it.timestamp}")
                }
            )
        }


        composable(BottomNavItem.Tolerance.screenRoute) {
            if(breakActive){
                BreakScreen(
                    navigateToAchievements = {
                        //TODO
                    },
                    navigateToAdvice = {
                        navController.navigate(BottomNavItem.Tolerance.screenRoute)
                    }
                )
            } else{
                ToleranceAdviceScreen(
                    navigateToBreak = {
                        navController.navigate(BottomNavItem.Tolerance.screenRoute)
                    }
                )
            }

        }

        composable(BottomNavItem.StrainSearch.screenRoute) {
            StrainSearchScreen()
        }

        composable(BottomNavItem.Premium.screenRoute) {
            PremiumScreen()
        }

        composable(
            route = "${AppScreens.AddSession.screenRoute}/{${Constants.ARGUMENT_SESSION_TIMESTAMP}}",
            arguments = listOf(
                navArgument(Constants.ARGUMENT_SESSION_TIMESTAMP) { type = NavType.LongType }
            )
        ) {
            val sessionTimestamp = it.arguments?.getLong(Constants.ARGUMENT_SESSION_TIMESTAMP) ?: 0
            AddSessionScreen(
                navigateToStrainInfo = {
                    //TODO
                },
                navigateToHighTest = {
                    //TODO
                },
                navigateHome = {
                    navController.navigate(BottomNavItem.Statistics.screenRoute)
                },
                sessionTimestamp = sessionTimestamp
            )
        }
    }
}