package com.eatmybrain.smoketracker.ui.screens.premium

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
fun PremiumScreen(viewModel: PremiumViewModel = hiltViewModel(), navigateToStatistics:()->Unit) {
    val price by viewModel.price.observeAsState()
    val purchaseError by viewModel.purchaseError.observeAsState()
    val purchaseSuccess by viewModel.purchaseSuccess.observeAsState()

    if (price == null) return
    if(purchaseSuccess==true) {
        LaunchedEffect(purchaseSuccess){
            navigateToStatistics()
            viewModel.purchaseResultHandled()
        }
    }

    Premium(
        onBuyClicked = { viewModel.purchase()},
        price = price!!,
        purchaseError = purchaseError!!
    )
}


@Composable
private fun Premium(onBuyClicked: () -> Unit, price: Int, purchaseError:Boolean) {
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
                .height(300.dp),
            showError = purchaseError
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
                price = 3,
                purchaseError = true
            )
        }
    }
}