package com.eatmybrain.smoketracker.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.ui.screens.premium.PremiumViewModel

@Composable
fun PremiumDialog(
    onDismiss: () -> Unit,
    viewModel: PremiumViewModel
) {
    val price by viewModel.price.observeAsState()
    val purchaseError by viewModel.purchaseError.observeAsState()
    val purchaseSuccess by viewModel.purchaseSuccess.observeAsState()

    if (purchaseSuccess == true) {
        LaunchedEffect(purchaseSuccess) {
            viewModel.purchaseResultHandled()
            onDismiss()
        }
    }

    PremiumDialogUI(
        onDismiss = {
            viewModel.purchaseResultHandled()
            onDismiss()
        },
        price = price,
        onBuyClicked = {
            viewModel.purchase()
        },
        purchaseError = purchaseError
    )

}

@Composable
private fun PremiumDialogUI(
    onDismiss: () -> Unit,
    price: Int?,
    onBuyClicked: () -> Unit,
    purchaseError: Boolean?
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        PremiumCard(
            onBuyClicked = { onBuyClicked() },
            price = price,
            modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
            showError = purchaseError!!
        )
    }
}

@Composable
fun PremiumCard(onBuyClicked: () -> Unit, price: Int?, modifier: Modifier, showError: Boolean) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
        modifier = modifier
    ) {
        Column {
            PremiumCardHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            )

            PremiumCardRow(
                text = stringResource(R.string.no_ads),
                modifier = Modifier.padding(start = 26.dp, top = 20.dp)
            )

            PremiumCardRow(
                text = stringResource(R.string.high_test),
                modifier = Modifier.padding(start = 26.dp, top = 16.dp)
            )


            PremiumCardRow(
                text = stringResource(R.string.achievements),
                modifier = Modifier.padding(start = 26.dp, top = 16.dp)
            )

            ButtonBuy(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(80.dp, 35.dp),
                onClick = onBuyClicked
            )

            if (price != null) {
                BottomText(
                    price = price,
                    showError = showError,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }


        }
    }
}

@Composable
private fun BottomText(price: Int, showError: Boolean, modifier: Modifier) {
    if (showError) {
        Text(
            modifier = modifier,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onError,
            text = stringResource(R.string.error)
        )
    } else {
        Text(
            text = "$price${stringResource(R.string.price)}",
            color = MaterialTheme.colors.secondary,
            style = MaterialTheme.typography.body1,
            modifier = modifier
        )
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ButtonBuy(modifier: Modifier, onClick: () -> Unit) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        onClick = onClick
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                text = stringResource(R.string.buy),
                color = MaterialTheme.colors.background,
                style = MaterialTheme.typography.subtitle1
            )
        }

    }
}


@Composable
private fun PremiumCardRow(text: String, modifier: Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Card(
            shape = CircleShape,
            modifier = Modifier.size(8.dp),
            backgroundColor = MaterialTheme.colors.primary
        ) {}

        Text(
            text = text,
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(start = 10.dp, bottom = 0.dp)
        )
    }
}

@Composable
private fun PremiumCardHeader(modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.premium),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground
        )

        Icon(
            painter = painterResource(R.drawable.ic_star),
            contentDescription = "Star icon",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}