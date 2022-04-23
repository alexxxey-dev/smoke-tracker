package com.eatmybrain.smoketracker.ui.screens.tolerance_break

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.ui.StyledButton
import com.eatmybrain.smoketracker.util.Constants


@Composable
fun BreakScreen(viewModel: BreakViewModel = hiltViewModel()) {
    Break(
        stopBreak = { viewModel.toggleBreak() }
    )
}

@Composable
fun Break(stopBreak: () -> Unit) {

    Column(
        modifier = Modifier
            .padding(
                start = 12.dp,
                end = 12.dp,
                bottom = Constants.BOTTOM_NAV_HEIGHT + 16.dp,
                top = 16.dp
            )
            .fillMaxSize()
    ) {
        StyledButton(
            text = stringResource(R.string.stop),
            onClick = stopBreak,
            icon = null,
            modifier = Modifier.height(45.dp)
        )
    }
}