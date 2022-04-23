package com.eatmybrain.smoketracker.ui.screens.break_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.ui.components.CircleProgressBar
import com.eatmybrain.smoketracker.ui.components.StyledButton
import com.eatmybrain.smoketracker.util.Constants


@Composable
fun BreakScreen(viewModel: BreakViewModel = hiltViewModel()) {
    val totalTime by viewModel.totalTime.observeAsState()
    val leftTime by viewModel.leftTime.observeAsState()

    if (totalTime == null || leftTime == null) return


    BreakScreenContent(
        stopBreak = { viewModel.toggleBreak() },
        modifier = Modifier
            .padding(
                start = 12.dp,
                end = 12.dp,
                bottom = Constants.BOTTOM_NAV_HEIGHT + 16.dp,
                top = 16.dp
            )
            .fillMaxSize(),
        totalTime = totalTime!!,
        leftTime = leftTime!!
    )
}

@Composable
fun BreakScreenContent(stopBreak: () -> Unit, modifier: Modifier, totalTime: Long, leftTime: Long) {
    Column(
        modifier =modifier
    ) {
        BreakProgress(
            totalTime = totalTime,
            leftTime = leftTime,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(240.dp)
        )
        StyledButton(
            text = stringResource(R.string.stop),
            onClick = stopBreak,
            icon = null,
            modifier = Modifier.height(45.dp)
        )
    }
}

@Composable
fun BreakProgress(totalTime:Long, leftTime:Long, modifier: Modifier = Modifier) {
    val percentage =leftTime.div(totalTime.toFloat())
    Box(modifier = modifier){
        CircleProgressBar(
            timeLeft = leftTime,
            percentage = percentage
        )
    }
}