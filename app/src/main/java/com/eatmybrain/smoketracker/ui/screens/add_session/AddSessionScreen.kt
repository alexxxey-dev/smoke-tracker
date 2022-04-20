package com.eatmybrain.smoketracker.ui.screens.add_session

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.ui.StyledButton
import com.eatmybrain.smoketracker.ui.screens.add_session.enums.AmountType
import com.eatmybrain.smoketracker.ui.screens.add_session.enums.string
import com.eatmybrain.smoketracker.ui.theme.SmokeTrackerTheme
import com.eatmybrain.smoketracker.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private val AlphaSecondary = 0.4f


@Composable
fun AddSessionScreen(
    viewModel: AddSessionViewModel = hiltViewModel(),
    navigateToStrainInfo: (String) -> Unit,
    navigateToHighTest: () -> Unit,
    navigateHome: () -> Unit
) {
    var strainName by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }
    var moodBefore by rememberSaveable { mutableStateOf(0f) }
    var moodAfter by rememberSaveable { mutableStateOf(0f) }
    var highStrength by rememberSaveable { mutableStateOf(0) }
    var amount by rememberSaveable { mutableStateOf("") }
    var amountType by rememberSaveable { mutableStateOf(AmountType.Gram.string()) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val onSaveClicked = {
        val errorStringId = viewModel.onSaveClicked(
            strainName = strainName,
            pricePerGram = price,
            moodBefore = moodBefore,
            moodAfter = moodAfter,
            highStrength = highStrength,
            amount = amount,
            amountType = amountType
        )
        if (errorStringId == null) {
            navigateHome()
        } else {
            val text = context.getString(errorStringId)
            scope.launch { snackbarHostState.showSnackbar(text) }
        }
    }

    Box(contentAlignment = Alignment.BottomCenter) {
        AddSession(
            strainName = strainName,
            price = price,
            moodAfter = moodAfter,
            moodBefore = moodBefore,
            highStrength = highStrength,
            amount = amount,
            amountType = amountType,
            onAmountTypeChanged = { amountType = it },
            onStrainNameChange = { strainName = it },
            onPriceChange = { price = it },
            onMoodBeforeChange = { moodBefore = it },
            onMoodAfterChange = { moodAfter = it },
            onHighStrengthChanged = { highStrength = it },
            onAmountChanged = { amount = it },
            onStrainInfoClicked = { navigateToStrainInfo(strainName) },
            onHighTestClicked = { navigateToHighTest() },
            onSaveClicked = { onSaveClicked() }
        )

        SnackbarHost(hostState = snackbarHostState)
    }


}



@Composable
private fun AddSession(
    strainName: String,
    price: String,
    onStrainNameChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onStrainInfoClicked: () -> Unit,
    moodBefore: Float,
    moodAfter: Float,
    onMoodAfterChange: (Float) -> Unit,
    onMoodBeforeChange: (Float) -> Unit,
    highStrength: Int,
    onHighStrengthChanged: (Int) -> Unit,
    onHighTestClicked: () -> Unit,
    amount: String,
    onAmountChanged: (String) -> Unit,
    onSaveClicked: () -> Unit,
    amountType: String,
    onAmountTypeChanged: (String) -> Unit
) {
    Column(
        Modifier
            .padding(top = 16.dp, start = 22.dp, end = 22.dp, bottom = 16.dp)
            .fillMaxSize()
    ) {
        Title(
            strainName = strainName,
            onStrainNameChange = onStrainNameChange,
            onStrainInfoClicked = onStrainInfoClicked
        )

        Price(
            price = price,
            onPriceChange = onPriceChange,
            modifier = Modifier.padding(top = 6.dp)
        )

        Mood(
            modifier = Modifier.padding(top = 30.dp),
            moodBefore = moodBefore,
            moodAfter = moodAfter,
            onMoodAfterChange = onMoodAfterChange,
            onMoodBeforeChange = onMoodBeforeChange
        )

        HighStrength(
            highStrength = highStrength,
            onHighStrengthChanged = onHighStrengthChanged,
            onHighTestClicked = onHighTestClicked,
            modifier = Modifier.padding(top = 20.dp)
        )

        Amount(
            amount = amount,
            onAmountChanged = onAmountChanged,
            modifier = Modifier
                .padding(top = 15.dp)
                .weight(1f),
            amountType = amountType,
            onAmountTypeChanged = onAmountTypeChanged
        )
        StyledButton(
            text = stringResource(R.string.save),
            onClick = onSaveClicked,
            icon = painterResource(R.drawable.ic_save),
            modifier = Modifier
                .height(50.dp)
                .align(Alignment.End)
        )
    }
}


@Composable
private fun Amount(
    amount: String,
    onAmountChanged: (String) -> Unit,
    amountType: String,
    onAmountTypeChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground.copy(alpha = AlphaSecondary),
            text = stringResource(R.string.amount)
        )

        Row(modifier = Modifier.padding(top = 7.dp)) {
            AmountEditText(amount = amount, onAmountChanged = onAmountChanged)
            AmountTypeDropdown(
                amountType = amountType,
                onAmountTypeChanged = onAmountTypeChanged,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}


@Composable
private fun AmountTypeDropdown(
    amountType: String,
    onAmountTypeChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val width = 130.dp
    val height = 30.dp
    //TODO fix dropdown animation
    AnimatedVisibility(expanded) {
        Dropdown(
            onAmountTypeChanged = onAmountTypeChanged,
            onDismiss = { expanded = false },
            height = height,
            modifier = modifier.width(width)
        )
    }

    AnimatedVisibility(!expanded) {
        DropdownStatus(
            amountType = amountType,
            modifier = modifier
                .height(height)
                .width(width),
            onExpand = { expanded = true }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DropdownStatus(
    amountType: String,
    modifier: Modifier = Modifier,
    onExpand: () -> Unit
) {
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        onClick = onExpand
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = amountType,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .padding(start = 15.dp)
                    .weight(1f)
            )

            Icon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = "Expand dropdown",
                modifier = Modifier.padding(end = 11.dp),
                tint = MaterialTheme.colors.onBackground.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun Dropdown(
    onAmountTypeChanged: (String) -> Unit,
    onDismiss: () -> Unit,
    height: Dp,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
        modifier = modifier
    ) {
        Column {
            val amountTypes = AmountType.values()
            amountTypes.forEach { type ->
                Row(
                    modifier = Modifier
                        .clickable {
                            onAmountTypeChanged(type)
                            onDismiss()
                        }
                        .fillMaxWidth()
                        .height(height),
                    verticalAlignment = CenterVertically
                ) {
                    Text(
                        text = type,
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 15.dp).weight(1f)
                    )
                    if(type==amountTypes.first()){
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_down),
                            contentDescription = "Expand dropdown",
                            modifier = Modifier.padding(end = 11.dp),
                            tint = MaterialTheme.colors.onBackground.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun AmountEditText(amount: String, onAmountChanged: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = amount,
            onValueChange = {
                if (it.length <= 2) onAmountChanged(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }),
            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
        )
    }
}

@Composable
private fun HighStrength(
    highStrength: Int,
    onHighStrengthChanged: (Int) -> Unit,
    onHighTestClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground.copy(alpha = AlphaSecondary),
            text = stringResource(R.string.high)
        )

        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SelectableCircleList(
                currentHighStrength = highStrength,
                onHighStrengthChanged = { onHighStrengthChanged(it) },
                modifier = Modifier.weight(1f)
            )
            Text(
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body1,
                text = stringResource(R.string.test_yourself),
                modifier = Modifier.clickable {
                    onHighTestClicked()
                }
            )
        }
    }
}

@Composable
private fun SelectableCircleList(
    currentHighStrength: Int,
    onHighStrengthChanged: (Int) -> Unit,
    modifier: Modifier
) {
    Row(modifier = modifier) {
        repeat(Constants.MAX_HIGH_STRENGTH) {
            SelectableCircle(
                currentStrength = currentHighStrength,
                localStrength = it + 1,
                onSelected = { strength -> onHighStrengthChanged(strength) },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .border(width = 1.dp, color = MaterialTheme.colors.primary, shape = CircleShape)
                    .clip(CircleShape)
                    .size(28.dp)
            )
        }
    }
}

@Composable
private fun SelectableCircle(
    currentStrength: Int,
    localStrength: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clickable {
                    if (currentStrength == localStrength) {
                        onSelected(0)
                    } else {
                        onSelected(localStrength)
                    }
                }
                .background(if (currentStrength == localStrength) MaterialTheme.colors.primary else Color.Transparent)
                .fillMaxSize()
        ) {
            Text(
                text = localStrength.toString(),
                color = if (currentStrength == localStrength) MaterialTheme.colors.surface else MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body2
            )
        }

    }
}

@Composable
private fun Mood(
    modifier: Modifier = Modifier,
    moodBefore: Float,
    moodAfter: Float,
    onMoodAfterChange: (Float) -> Unit,
    onMoodBeforeChange: (Float) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.mood_before),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground.copy(alpha = AlphaSecondary)
        )
        Slider(
            value = moodBefore,
            onValueChange = onMoodBeforeChange,
            valueRange = 0f..100f,
            modifier = Modifier.height(25.dp)
        )
        Text(
            text = stringResource(R.string.mood_after),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground.copy(alpha = AlphaSecondary),
            modifier = Modifier.padding(top = 12.dp)
        )
        Slider(
            value = moodAfter,
            onValueChange = onMoodAfterChange,
            valueRange = 0f..100f,
            modifier = Modifier.height(25.dp)
        )

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Title(
    modifier: Modifier = Modifier,
    strainName: String,
    onStrainNameChange: (String) -> Unit,
    onStrainInfoClicked: () -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        TitleEditText(
            strainName = strainName,
            onStrainNameChange = onStrainNameChange,
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
        )

        Card(onClick = { onStrainInfoClicked() }, shape = CircleShape) {
            Icon(
                painter = painterResource(R.drawable.ic_info),
                modifier = Modifier.size(20.dp),
                contentDescription = "Strain info",
                tint = MaterialTheme.colors.onBackground.copy(alpha = AlphaSecondary)
            )
        }

    }
}

@Composable
private fun TitleEditText(
    strainName: String,
    onStrainNameChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    Box(modifier = modifier) {
        if (strainName.isEmpty()) {
            Text(
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onBackground.copy(alpha = AlphaSecondary),
                text = stringResource(R.string.strain_name)
            )
        }
        BasicTextField(
            value = strainName,
            onValueChange = onStrainNameChange,
            textStyle = MaterialTheme.typography.h1,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}


@Composable
private fun Price(
    modifier: Modifier = Modifier,
    price: String,
    onPriceChange: (String) -> Unit
) {
    Row(modifier = modifier) {
        PriceEditText(price = price, onPriceChange = onPriceChange)

        Text(
            text = "$",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PriceEditText(
    price: String,
    onPriceChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Box(modifier = modifier) {
        if (price.isEmpty()) {
            Text(
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground.copy(alpha = AlphaSecondary),
                text = stringResource(R.string.price_per_gram)
            )
        }
        BasicTextField(
            value = price,
            onValueChange = { if (it.length <= 4) onPriceChange(it) },
            textStyle = MaterialTheme.typography.body2,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            })
        )
    }
}

@Preview
@Composable
fun AddSessionPreview() {
    SmokeTrackerTheme {
        Scaffold {
            AddSession(
                strainName = "",
                price = "",
                onStrainInfoClicked = {},
                onStrainNameChange = {},
                onPriceChange = {},
                moodAfter = 0.0f,
                moodBefore = 0.0f,
                onMoodAfterChange = {},
                onMoodBeforeChange = {},
                highStrength = 0,
                onHighTestClicked = {},
                onHighStrengthChanged = {},
                amount = "",
                onAmountChanged = {},
                onSaveClicked = {},
                amountType = AmountType.Gram.toString(),
                onAmountTypeChanged = {}
            )
        }
    }
}