package com.eatmybrain.smoketracker.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.eatmybrain.smoketracker.ui.navigation.BottomNavItem.Companion.isVisible
import com.eatmybrain.smoketracker.ui.screens.break_screen.BreakViewModel
import com.eatmybrain.smoketracker.ui.screens.premium.PremiumViewModel
import com.eatmybrain.smoketracker.util.Constants
import com.eatmybrain.smoketracker.util.currentRoute

@Composable
fun BottomNav(
    navController: NavController,
    premiumViewModel: PremiumViewModel = hiltViewModel(),
    breakViewModel: BreakViewModel = hiltViewModel()
) {
    val hasPremium by premiumViewModel.hasPremium.observeAsState()
    val breakActive by breakViewModel.isBreakActive.observeAsState()
    val items =  BottomNavItem.values().filter { it.isVisible(hasPremium, breakActive) }
    if(hasPremium==null) return

    BottomNavigation(
        items = items,
        navController = navController
    )
}

@Composable
private fun BottomNavigation(
    items: List<BottomNavItem>,
    navController: NavController
) {
    fun onItemClick(item:BottomNavItem){
        navController.navigate(item.screenRoute) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.height(Constants.BOTTOM_NAV_HEIGHT)
    ) {
        val currentRoute = currentRoute(navController)
        items.forEach { item ->
            BottomNavItem(
                item = item,
                onItemClick = {
                    onItemClick(item)
                },
                currentRoute = currentRoute
            )
        }
    }
}

@Composable
private fun RowScope.BottomNavItem(item: BottomNavItem, onItemClick:() ->Unit, currentRoute:String?){
    BottomNavigationItem(
        icon = {
            Icon(
                painter = painterResource(id = item.icon),
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
        onClick =onItemClick
    )
}

