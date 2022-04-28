package com.eatmybrain.smoketracker.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.eatmybrain.smoketracker.R


@Composable
fun StopBreakDialog(onDismiss: () -> Unit, onYesClicked: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        StopBreakDialogUI(
            onYesClicked = onYesClicked,
            modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp)
        )
    }
}

@Composable
private fun StopBreakDialogUI(onYesClicked: () -> Unit, modifier: Modifier) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(R.string.warning),
                style = MaterialTheme.typography.h1
            )

            Text(
                modifier = Modifier.padding(top = 16.dp, start = 22.dp, end = 22.dp),
                text = stringResource(R.string.tolerance_break_stop),
                style = MaterialTheme.typography.body1
            )


            StyledButton(
                text = stringResource(R.string.yes),
                onClick = onYesClicked,
                modifier = Modifier
                    .padding(end = 12.dp, bottom = 12.dp, top = 22.dp)
                    .height(40.dp)
                    .align(Alignment.End)
            )
        }
    }
}