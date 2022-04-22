package com.eatmybrain.smoketracker.ui.screens.tolerance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.ui.StyledButton
import com.eatmybrain.smoketracker.util.Constants

@Composable
fun ToleranceScreen(viewModel:ToleranceViewModel = hiltViewModel(), onStartBreak:()->Unit) {
    Column(
        modifier = Modifier
            .padding(
                top = 16.dp,
                start = 22.dp,
                end = 22.dp,
                bottom = 16.dp + Constants.BOTTOM_NAV_HEIGHT
            )
            .fillMaxSize()
    ) {

        Text(
            text = stringResource(R.string.advice),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground
        )

        AdviceList(
            modifier = Modifier
                .padding(top = 8.dp)
                .weight(1f)
        )

        StyledButton(
            text = stringResource(R.string.reset_tolerance),
            onClick =  {
               //TODO
            },
            icon = painterResource(R.drawable.ic_reset),
            modifier = Modifier
                .height(50.dp)
                .align(Alignment.End)
        )
    }
}


@Composable
fun AdviceList(modifier: Modifier) {
    val advices = stringArrayResource(R.array.advices)
    Column(modifier) {
        advices.forEach {
            AdviceItem(
                advice = it,
                modifier = Modifier.padding(top = 20.dp)
            )
        }
    }

}


@Composable
fun AdviceItem(advice: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Card(
            shape = CircleShape,
            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier.size(8.dp)
        ) {}

        Text(
            text = advice,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(start = 20.dp)
        )
    }
}
