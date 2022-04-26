package com.eatmybrain.smoketracker.ui.screens.achievements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.data.structs.Achievement
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@Composable
fun AchievementsScreen(
    viewModel: AchievementsViewModel = hiltViewModel()
) {
    val achievementList by viewModel.achievementsList.observeAsState()
    val completedCount by viewModel.completedCount.observeAsState()
    if (achievementList == null || completedCount == null) return

    Achievements(
        achievementList = achievementList!!,
        completedCount = completedCount!!,
        totalCount = achievementList!!.size
    )
}

@Composable
private fun Achievements(
    achievementList: List<Achievement>,
    completedCount: Int,
    totalCount: Int
) {
    Column(
        modifier = Modifier.padding(top = 16.dp, start = 22.dp, end = 22.dp, bottom = 16.dp)
    ) {
        Header(
            completedCount = completedCount,
            totalCount = totalCount,
            modifier = Modifier.fillMaxWidth()
        )
        
        AchievementsViewPager(
            achievementList = achievementList,
            modifier = Modifier.padding(top = 30.dp)
        )
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
private fun AchievementsViewPager(
    achievementList: List<Achievement>,
    modifier: Modifier
) {
    val pagerState = rememberPagerState()
    Column(modifier = modifier) {
        HorizontalPager(
            count = 3,
            state = pagerState,
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            AchievementsPage(
                page = page,
                modifier = Modifier.fillMaxWidth(),
                achievementList = achievementList
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.align(CenterHorizontally).padding(top = 32.dp),
            activeColor = MaterialTheme.colors.primary,
            inactiveColor = MaterialTheme.colors.secondary
        )
    }

}

@Composable
private fun AchievementsPage(
    page: Int,
    modifier: Modifier,
    achievementList: List<Achievement>
) {
    AchievementItem(
        achievement = achievementList.first(),
        modifier = Modifier.size(90.dp)
    )
}

@Composable
private fun AchievementItem(
    achievement: Achievement,
    modifier: Modifier
){
    val iconResource = if(achievement.achieved) R.drawable.ic_completed else R.drawable.ic_locked
    val iconTint = if(achievement.achieved) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp
    ){
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = achievement.number.toString(),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.subtitle1
            )


            Text(
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 8.dp),
                text = achievement.description
            )

            Icon(
                painter = painterResource(iconResource),
                contentDescription = "Achievement status",
                modifier = Modifier.padding(top = 12.dp),
                tint = iconTint
            )
        }
    }
}

@Composable
private fun Header(
    completedCount: Int,
    totalCount: Int,
    modifier: Modifier
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = stringResource(R.string.my_achievements),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground
        )

        Text(
            text = "${completedCount}/${totalCount}",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.secondary
        )
    }
}