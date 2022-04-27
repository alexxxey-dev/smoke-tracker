package com.eatmybrain.smoketracker.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.MutableLiveData
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.ui.theme.BrightGreen

@Composable
fun PremiumDialog(showDialog:MutableState<Boolean>, onBuyClicked: () -> Unit, price: Int){
    Dialog(onDismissRequest = {
        showDialog.value = false
    }){
        PremiumCard(onBuyClicked = { onBuyClicked() }, price = price)
    }
}


@Composable
fun PremiumCard(onBuyClicked: () -> Unit, price:Int) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 45.dp)
            .height(300.dp)
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


            Text(
                text = "$price${stringResource(R.string.price)}",
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )


        }
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