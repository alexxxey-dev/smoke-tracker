package com.eatmybrain.smoketracker.ui.screens.tolerance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.ui.StyledButton
import com.eatmybrain.smoketracker.ui.theme.SmokeTrackerTheme
import com.eatmybrain.smoketracker.util.Constants
import com.eatmybrain.smoketracker.util.countCommas
import com.eatmybrain.smoketracker.util.removeCommas

@Composable
fun ToleranceScreen(
    viewModel: ToleranceViewModel = hiltViewModel(),
    navigateToResetTolerance: () -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }
    val dialogError = remember { mutableStateOf("") }


    Tolerance(
        saveSmokeInfo = { freq, amount, price ->
            return@Tolerance viewModel.saveSmokeInfo(freq, amount, price)
        },
        navigateToResetTolerance = navigateToResetTolerance,
        dialogError = dialogError,
        showDialog = showDialog
    )
}

@Composable
private fun Tolerance(
    saveSmokeInfo: (String, String, String) -> Int?,
    navigateToResetTolerance: () -> Unit,
    dialogError: MutableState<String>,
    showDialog: MutableState<Boolean>
) {
    val context = LocalContext.current
    SmokeInfoDialog(
        saveSmokeInfo = { freq, amount, price ->
            val result = saveSmokeInfo(freq, amount, price)
            if (result == null) {
                showDialog.value = false
                dialogError.value = ""
                navigateToResetTolerance()
            } else {
                val text = context.getString(result)
                dialogError.value = text
            }
        },
        showDialog = showDialog,
        dialogError = dialogError
    )

    ToleranceScreenContent(showDialog)
}

@Composable
private fun SmokeInfoDialog(
    saveSmokeInfo: (String, String, String) -> Unit,
    showDialog: MutableState<Boolean>,
    dialogError: MutableState<String>
) {
    var smokeFreq by remember { mutableStateOf("") }
    var smokeAmount by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    if (showDialog.value) {
        Dialog(
            onDismissRequest = { showDialog.value = false }
        ) {
            SmokeInfoDialogUI(
                onSaveClicked = { saveSmokeInfo(smokeFreq, smokeAmount, price) },
                smokeFreq = smokeFreq,
                smokeAmount = smokeAmount,
                price = price,
                onSmokeAmountUpdate = { smokeAmount = it },
                onSmokeFreqUpdate = { smokeFreq = it },
                onPriceUpdate = { price = it },
                dialogError = dialogError
            )
        }

    }
}


@Composable
private fun SmokeInfoDialogUI(
    onSaveClicked: () -> Unit,
    dialogError: MutableState<String>,
    smokeFreq: String,
    smokeAmount: String,
    price: String,
    onSmokeFreqUpdate: (String) -> Unit,
    onSmokeAmountUpdate: (String) -> Unit,
    onPriceUpdate: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(300.dp)
            .wrapContentHeight(),
        elevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = stringResource(R.string.how_often_smoke),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground.copy(alpha = Constants.ALPHA_GREY),
                modifier = Modifier.padding(top = 20.dp)
            )

            EditTextRow(
                value = smokeFreq,
                onValueChange = onSmokeFreqUpdate,
                descriptionText = stringResource(R.string.times_a_week),
                modifier = Modifier.padding(top = 12.dp),
                allowCommas = false,
                maxChars = 2,
                imeAction = ImeAction.Next
            )

            Text(
                text = stringResource(R.string.how_much_smoke),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground.copy(alpha = Constants.ALPHA_GREY),
                modifier = Modifier.padding(top = 16.dp)
            )

            EditTextRow(
                value = smokeAmount,
                onValueChange = onSmokeAmountUpdate,
                descriptionText = stringResource(R.string.grams),
                modifier = Modifier.padding(top = 12.dp),
                allowCommas = true,
                maxChars = 2,
                imeAction = ImeAction.Next
            )
            Text(
                text = stringResource(R.string.average_price),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground.copy(alpha = Constants.ALPHA_GREY),
                modifier = Modifier.padding(top = 16.dp)
            )

            EditTextRow(
                value = price,
                onValueChange = onPriceUpdate,
                descriptionText = stringResource(R.string.dollars),
                modifier = Modifier.padding(top = 12.dp),
                allowCommas = true,
                maxChars = 3,
                imeAction = ImeAction.Done
            )

            StyledButton(
                text = stringResource(R.string.save),
                onClick = onSaveClicked,
                icon = painterResource(R.drawable.ic_save),
                modifier = Modifier
                    .padding(top = 16.dp, end = 16.dp, bottom = 16.dp)
                    .align(Alignment.End)
                    .height(40.dp)

            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun EditTextRow(
    value: String,
    onValueChange: (String) -> Unit,
    descriptionText: String,
    modifier: Modifier = Modifier,
    allowCommas: Boolean,
    maxChars: Int,
    imeAction: ImeAction
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.width(40.dp)) {
            BasicTextField(
                value = value,
                onValueChange = {
                    val lengthOk = it.removeCommas().length <= maxChars
                    val commasOk = if (allowCommas) it.countCommas() <= 1 else it.countCommas() == 0
                    if (lengthOk && commasOk) {
                        onValueChange(it)
                    }
                },
                textStyle = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center
                ),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = imeAction,
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Number
                )
            )

            Divider(
                color = MaterialTheme.colors.primary,
                thickness = 1.dp,
                modifier = Modifier.padding(top = 1.dp)
            )
        }


        Text(
            text = descriptionText,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(start = 11.dp)
        )
    }
}

@Composable
private fun ToleranceScreenContent(
    showDialog: MutableState<Boolean>
) {
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
            onClick = { showDialog.value = true },
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


@Preview
@Composable
private fun TolerancePreview() {
    SmokeTrackerTheme {
        Scaffold {
            Tolerance(
                saveSmokeInfo = { freq, amount, price ->
                    return@Tolerance null
                },
                navigateToResetTolerance = {},
                dialogError = remember { mutableStateOf("") },
                showDialog = remember { mutableStateOf(true) }
            )
        }
    }
}