package com.eatmybrain.smoketracker.ui.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.eatmybrain.smoketracker.util.Constants
import com.eatmybrain.smoketracker.util.currentRoute

@Composable
fun BottomNav(navController: NavController) {
    val items = listOf(
        BottomNavItem.Statistics,
        BottomNavItem.StrainSearch,
        BottomNavItem.Tolerance,
        BottomNavItem.Premium
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.height(Constants.BOTTOM_NAV_HEIGHT)
    ) {
        val currentRoute = currentRoute(navController)
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.secondary,
                alwaysShowLabel = true,
                selected = currentRoute == item.screenRoute,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

