package com.eatmybrain.smoketracker.ui.screens.premium

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.ui.components.PremiumCard
import com.eatmybrain.smoketracker.ui.theme.SmokeTrackerTheme
import com.eatmybrain.smoketracker.util.Constants


@Composable
fun PremiumScreen(viewModel: PremiumViewModel = hiltViewModel()) {
    val price by viewModel.price.observeAsState()


    if (price == null) return


    Premium(
        onBuyClicked = {
            viewModel.purchase()
        },
        price = price!!
    )
}


@Composable
private fun Premium(onBuyClicked: () -> Unit, price: Int) {


    Box(
        modifier = Modifier
            .padding(bottom = Constants.BOTTOM_NAV_HEIGHT)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.premium_background),
            contentDescription = "Screen background",
            modifier = Modifier.fillMaxSize()
        )


        PremiumCard(
            onBuyClicked = onBuyClicked, price = price, modifier = Modifier
                .width(310.dp)
                .height(300.dp)
        )

    }
}


@Preview
@Composable
private fun PremiumPreview() {
    SmokeTrackerTheme {
        Scaffold {
            it.calculateBottomPadding()
            Premium(
                onBuyClicked = {},
                price = 3
            )
        }
    }
}