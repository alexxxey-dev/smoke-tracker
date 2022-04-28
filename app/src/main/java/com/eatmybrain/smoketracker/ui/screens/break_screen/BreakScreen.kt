package com.eatmybrain.smoketracker.ui.screens.break_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
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
import com.eatmybrain.smoketracker.ui.components.CircleProgressBar
import com.eatmybrain.smoketracker.ui.components.Loading
import com.eatmybrain.smoketracker.ui.components.StyledButton
import com.eatmybrain.smoketracker.ui.screens.premium.PremiumViewModel
import com.eatmybrain.smoketracker.ui.theme.SmokeTrackerTheme
import com.eatmybrain.smoketracker.util.Time


@Composable
fun BreakScreen(
    breakViewModel: BreakViewModel = hiltViewModel(),
    premiumViewModel:PremiumViewModel = hiltViewModel(),
    navigateToAchievements: () -> Unit,
    navigateToAdvice:()->Unit
) {
    val breakActive = breakViewModel.isBreakActive.observeAsState()
    val totalTime by breakViewModel.totalTime.observeAsState()
    val leftTime by breakViewModel.leftTime.observeAsState()
    val hasPremium by premiumViewModel.hasPremium.observeAsState()
    val gramsAvoided by breakViewModel.gramsAvoided.observeAsState()
    val moneySaved by breakViewModel.moneySaved.observeAsState()
    val weedFreeTime by breakViewModel.weedFreeTime.observeAsState()

    LaunchedEffect(breakActive){
        breakViewModel.init()
    }

    if (totalTime == null || leftTime == null || gramsAvoided == null || moneySaved == null || weedFreeTime == null) {
        Loading()
    } else {
        BreakScreenContent(
            stopBreak = {
                breakViewModel.stopBreak()
                navigateToAdvice()
            },
            totalTime = totalTime!!,
            leftTime = leftTime!!,
            gramsAvoided = gramsAvoided!!,
            moneySaved = moneySaved!!,
            weedFreeTime = weedFreeTime!!,
            navigateToAchievements = {
                if(hasPremium == true){
                    navigateToAchievements()
                    navigateToAdvice()
                } else{
                    //TODO show dialog
                }
            }
        )
    }


}


@Composable
fun BreakScreenContent(
    stopBreak: () -> Unit,
    totalTime: Long,
    leftTime: Long,
    gramsAvoided: String,
    moneySaved: String,
    weedFreeTime: String,
    navigateToAchievements: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val statsCardCount = 3
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 19.dp,
                    end = 19.dp,
                    top = 16.dp
                )
        ) {
            StyledButton(
                text = stringResource(R.string.stop),
                onClick = { stopBreak() },
                modifier = Modifier.height(40.dp),
                icon = null
            )


            Icon(
                painter = painterResource(R.drawable.ic_achievements),
                contentDescription = "Achievements button",
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { navigateToAchievements() }
                    .size(30.dp)
                    .padding(top = 3.dp),
                tint = MaterialTheme.colors.primary
            )
        }



        BreakProgress(
            totalTime = totalTime,
            leftTime = leftTime,
            modifier = Modifier
                .padding(top = 45.dp)
                .align(Alignment.CenterHorizontally),
            radius = 120.dp,
            strokeWidth = 18.dp
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(top = 60.dp)
                .fillMaxWidth()
        ) {
            StatsCard(
                title = stringResource(R.string.money_saved),
                subtitle = moneySaved,
                cardsCount = statsCardCount
            )
            StatsCard(
                title = stringResource(R.string.weed_free_time),
                subtitle = weedFreeTime,
                cardsCount = statsCardCount
            )
            StatsCard(
                title = stringResource(R.string.grams_not_smoked),
                subtitle = gramsAvoided,
                cardsCount = statsCardCount
            )
        }


    }
}


@Composable
fun StatsCard(title: String, subtitle: String, cardsCount: Int) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = (screenWidth - (10.dp * cardsCount)) / 3
    val cardHeight = cardWidth.times(1.1f)
    Card(
        modifier = Modifier
            .size(width = cardWidth, height = cardHeight)
            .padding(start = 3.dp, end = 3.dp),
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
fun BreakProgress(
    totalTime: Long,
    leftTime: Long,
    modifier: Modifier = Modifier,
    radius: Dp,
    strokeWidth: Dp
) {
    val percentage = 1f - leftTime.div(totalTime.toFloat())
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(radius * 2f)
    ) {
        CircleProgressBar(radius = radius, strokeWidth = strokeWidth, percentage = percentage)
        TimeText(leftTime)
    }
}


@Composable
private fun TimeText(timeLeft: Long) {
    val minutes = Time.millisToMinutes(timeLeft) % 60
    val hours = Time.millisToHours(timeLeft) % 24
    val days = Time.millisToDays(timeLeft)

    Row {
        TimeTextItem(
            title = days.toString(),
            subtitle = stringResource(R.string.days),
            modifier = Modifier.padding(end = 20.dp)
        )

        TimeTextItem(
            title = hours.toString(),
            subtitle = stringResource(R.string.hours),
            modifier = Modifier.padding(end = 20.dp)
        )

        TimeTextItem(
            title = minutes.toString(),
            subtitle = stringResource(R.string.minutes)
        )
    }
}

@Composable
private fun TimeTextItem(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.primary
        )

        Text(
            text = subtitle,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground
        )
    }

}


@Composable
@Preview
private fun Preview() {
    SmokeTrackerTheme {
        Scaffold {
            it.calculateBottomPadding()
            Column {
                Spacer(Modifier.size(20.dp))
                CircleProgressBar(radius = 150.dp, strokeWidth = 15.dp, percentage = 1f)
            }


        }
    }
}