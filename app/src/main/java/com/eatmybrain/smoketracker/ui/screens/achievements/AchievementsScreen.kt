package com.eatmybrain.smoketracker.ui.screens.achievements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.data.structs.Achievement
import com.eatmybrain.smoketracker.data.structs.AchievementType
import com.eatmybrain.smoketracker.ui.theme.SmokeTrackerTheme
import com.eatmybrain.smoketracker.util.Constants
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
    Column() {
        Header(
            completedCount = completedCount,
            totalCount = totalCount,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 22.dp, end = 22.dp)
        )

        AchievementsViewPager(
            achievementList = achievementList,
            modifier = Modifier.padding(top = 16.dp)
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
    val chunks = achievementList.chunked(Constants.ACHIEVEMENTS_PER_PAGE)
    Column(modifier = modifier) {
        HorizontalPager(
            count = chunks.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .align(CenterHorizontally)
        ) { page ->
            AchievementsPage(
                modifier = Modifier,
                achievementList = chunks[page]
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 30.dp),
            activeColor = MaterialTheme.colors.primary,
            inactiveColor = MaterialTheme.colors.secondary
        )
    }

}

@Composable
private fun AchievementsPage(
    modifier: Modifier,
    achievementList: List<Achievement>
) {
    val rowItems = achievementList.chunked(Constants.ACHIEVEMENTS_PER_ROW)
    Column(modifier = modifier, horizontalAlignment = CenterHorizontally) {
        rowItems.forEach { items ->
            AchievementsRow(
                items = items,
                modifier = Modifier.padding(top = 20.dp)
            )
        }
    }

}

@Composable
private fun AchievementsRow(items: List<Achievement>, modifier: Modifier) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardPadding = 16.dp
    val horizontalPadding = 14.dp
    val cardSize =
        (screenWidth - (cardPadding * Constants.ACHIEVEMENTS_PER_ROW) - (horizontalPadding * 2)) / Constants.ACHIEVEMENTS_PER_ROW
    Row(modifier = modifier.padding(horizontal = horizontalPadding)) {
        items.forEach { achievement ->
            AchievementItem(
                achievement = achievement,
                modifier = Modifier.padding(horizontal = cardPadding / 2),
                size = cardSize
            )
        }
    }
}

@Composable
private fun AchievementItem(
    achievement: Achievement,
    modifier: Modifier = Modifier,
    size: Dp
) {
    val iconResource = if (achievement.achieved) R.drawable.ic_completed else R.drawable.ic_locked
    val iconTint =
        if (achievement.achieved) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
        modifier = modifier
    ) {
        Column(horizontalAlignment = CenterHorizontally, modifier = Modifier.size(size)) {
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = achievement.number.toString(),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )


            Text(
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body2.copy(
                    lineHeight = MaterialTheme.typography.subtitle1.lineHeight
                ),
                text = achievement.description,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )

            Icon(
                painter = painterResource(iconResource),
                contentDescription = "Achievement status",
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .size(17.dp),
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
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
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


@Preview
@Composable
private fun Preview() {
    val description = "Bla bla bla"
    val type = AchievementType.MONEY
    val moneySaved = 100
    val list = listOf(
        Achievement(
            number = 25,
            description = description,
            type = type,
            achieved = moneySaved >= 25,
            index = 0
        ),
        Achievement(
            number = 50,
            description = description,
            type = type,
            achieved = moneySaved >= 50,
            index = 0
        ),
        Achievement(
            number = 100,
            description = description,
            type = type,
            achieved = moneySaved >= 100,
            index = 0
        ),
        Achievement(
            number = 250,
            description = description,
            type = type,
            achieved = moneySaved >= 250,
            index = 0
        ),
        Achievement(
            number = 500,
            description = description,
            type = type,
            achieved = moneySaved >= 500,
            index = 0
        ),
        Achievement(
            number = 1000,
            description = description,
            type = type,
            achieved = moneySaved >= 1000,
            index = 0
        )
    )
    SmokeTrackerTheme {
        Scaffold() { paddingValues ->
            paddingValues.calculateBottomPadding()

            Achievements(
                achievementList = list,
                completedCount = list.count { it.achieved },
                totalCount = list.size
            )
        }

    }
}