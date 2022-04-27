package com.eatmybrain.smoketracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.eatmybrain.smoketracker.ui.screens.achievements.AchievementsScreen
import com.eatmybrain.smoketracker.ui.screens.add_session.AddSessionScreen
import com.eatmybrain.smoketracker.ui.screens.break_screen.BreakScreen
import com.eatmybrain.smoketracker.ui.screens.break_screen.BreakViewModel
import com.eatmybrain.smoketracker.ui.screens.premium.PremiumScreen
import com.eatmybrain.smoketracker.ui.screens.statistics.StatisticsScreen
import com.eatmybrain.smoketracker.ui.screens.strain_search.StrainSearchScreen
import com.eatmybrain.smoketracker.ui.screens.tolerance_advice.ToleranceAdviceScreen
import com.eatmybrain.smoketracker.util.Constants

@Composable
fun NavigationGraph(navController: NavHostController) {


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

        composable(AppScreens.Achievements.screenRoute) {
            AchievementsScreen()
        }

        composable(BottomNavItem.ToleranceBreak.screenRoute){
            BreakScreen(
                navigateToAchievements = {
                    navController.navigate(AppScreens.Achievements.screenRoute)
                },
                navigateToAdvice = {
                    navController.navigate(BottomNavItem.ToleranceAdvice.screenRoute)
                }
            )
        }
        composable(BottomNavItem.ToleranceAdvice.screenRoute) {
            ToleranceAdviceScreen(navigateToBreak = {
                navController.navigate(BottomNavItem.ToleranceBreak.screenRoute)
            })

        }

        composable(BottomNavItem.StrainSearch.screenRoute) {
            StrainSearchScreen()
        }

        composable(BottomNavItem.Premium.screenRoute) {
            PremiumScreen()
        }


        composable(
            route = "${AppScreens.AddSession.screenRoute}/{${Constants.ARGUMENT_SESSION_ID}}",
            arguments = listOf(
                navArgument(Constants.ARGUMENT_SESSION_ID) { type = NavType.LongType }
            )
        ) {
            val sessionId = it.arguments?.getLong(Constants.ARGUMENT_SESSION_ID) ?: 0
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
                sessionId = sessionId
            )
        }
    }
}


