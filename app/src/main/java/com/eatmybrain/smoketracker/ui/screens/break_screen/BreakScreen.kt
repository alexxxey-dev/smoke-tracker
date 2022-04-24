package com.eatmybrain.smoketracker.ui.screens.break_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.ui.components.CircleProgressBar
import com.eatmybrain.smoketracker.ui.components.StyledButton
import com.eatmybrain.smoketracker.util.Constants


@Composable
fun BreakScreen(
    viewModel: BreakViewModel = hiltViewModel(),
    navigateToAchievements: () -> Unit,
    navigateToAdvice: () -> Unit
) {
    val totalTime by viewModel.totalTime.observeAsState()
    val leftTime by viewModel.leftTime.observeAsState()

    val gramsAvoided by viewModel.gramsAvoided.observeAsState()
    val moneySaved by viewModel.moneySaved.observeAsState()
    val weedFreeTime by viewModel.weedFreeTime.observeAsState()
    if (totalTime == null || leftTime == null || gramsAvoided == null || moneySaved == null || weedFreeTime == null) return


    BreakScreenContent(
        stopBreak = {
            viewModel.toggleBreak()
            navigateToAdvice()
        },
        modifier = Modifier
            .padding(
                start = 12.dp,
                end = 12.dp,
                bottom = Constants.BOTTOM_NAV_HEIGHT + 16.dp,
                top = 30.dp
            )
            .fillMaxSize(),
        totalTime = totalTime!!,
        leftTime = leftTime!!,
        gramsAvoided = gramsAvoided!!,
        moneySaved = moneySaved!!,
        weedFreeTime = weedFreeTime!!,
        navigateToAchievements = navigateToAchievements
    )
}

@Composable
fun BreakScreenContent(
    stopBreak: () -> Unit,
    modifier: Modifier,
    totalTime: Long,
    leftTime: Long,
    gramsAvoided: String,
    moneySaved: String,
    weedFreeTime: String,
    navigateToAchievements: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        BreakProgress(
            totalTime = totalTime,
            leftTime = leftTime,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(240.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .weight(1f)
                .padding(top = 60.dp)
                .fillMaxWidth()
        ) {
            StatsCard(
                title = stringResource(R.string.money_saved),
                subtitle = moneySaved,
                modifier = Modifier.padding(end = 20.dp)
            )
            StatsCard(
                title = stringResource(R.string.weed_free_time),
                subtitle = weedFreeTime,
                modifier = Modifier.padding(end = 20.dp)
            )
            StatsCard(title = stringResource(R.string.grams_not_smoked), subtitle = gramsAvoided)
        }


        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            StyledButton(
                text = stringResource(R.string.stop),
                onClick = stopBreak,
                icon = null,
                modifier = Modifier
                    .height(45.dp)
                    .padding(start = 6.dp)
            )

            StyledButton(
                text = stringResource(R.string.achievements),
                onClick = navigateToAchievements,
                icon = painterResource(R.drawable.ic_achievements),
                modifier = Modifier
                    .height(45.dp)
                    .padding(end = 6.dp)
            )
        }

    }
}

@Composable
fun StatsCard(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.size(width = 100.dp, height = 110.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }
    }
}

@Composable
fun BreakProgress(totalTime: Long, leftTime: Long, modifier: Modifier = Modifier) {
    val percentage = leftTime.div(totalTime.toFloat())
    Box(modifier = modifier) {
        CircleProgressBar(
            timeLeft = leftTime,
            percentage = percentage
        )
    }
}