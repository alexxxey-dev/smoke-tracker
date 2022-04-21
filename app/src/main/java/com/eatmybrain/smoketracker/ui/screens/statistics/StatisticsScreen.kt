package com.eatmybrain.smoketracker.ui.screens.statistics

import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.data.structs.Session
import com.eatmybrain.smoketracker.ui.screens.statistics.enums.SessionsPeriod
import com.eatmybrain.smoketracker.util.Constants
import com.eatmybrain.smoketracker.util.Time
import com.eatmybrain.smoketracker.util.formatZero
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = hiltViewModel(),
    onAddSession: () -> Unit,
    onSessionClicked: (Session) -> Unit
) {
    val currentPeriod by viewModel.sessionsPeriod.observeAsState()
    val sessionsLineData by viewModel.sessionsLineData.observeAsState()
    val moneySpent by viewModel.moneySpent.observeAsState()
    val sessionList by viewModel.sessionList.observeAsState()

    if (currentPeriod != null
        && sessionsLineData != null
        && moneySpent != null
        && sessionList != null
    ) {
        Statistics(
            onPeriodSelected = { viewModel.updateSessionsPeriod(it) },
            currentPeriod = currentPeriod!!,
            sessionsLineData = sessionsLineData!!,
            moneySpent = moneySpent!!,
            onAddSession = onAddSession,
            sessionList = sessionList!!,
            onSessionClicked = onSessionClicked
        )
    }
}

@Composable
private fun Statistics(
    currentPeriod: SessionsPeriod,
    sessionsLineData: LineDataSet,
    moneySpent: Double,
    sessionList: List<Session>,
    onPeriodSelected: (SessionsPeriod) -> Unit,
    onAddSession: () -> Unit,
    onSessionClicked:(Session)->Unit
) {

    Column {
        HeaderRow(
            onPeriodSelected = onPeriodSelected,
            currentPeriod = currentPeriod,
            modifier = Modifier
                .padding(top = 16.dp, start = 22.dp, end = 22.dp)
        )

        StatisticsChart(
            sessionsLineData = sessionsLineData,
            modifier = Modifier
                .height(Constants.CHART_HEIGHT)
                .padding(start = 16.dp, top = 4.dp)
        )

        SessionsInfoRow(
            moneySpent = moneySpent,
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 10.dp),
            onAddSession = onAddSession
        )

        if(sessionList.isEmpty()){
            EmptyText()
        } else{
            SessionList(
                sessionList = sessionList,
                modifier = Modifier.padding(
                    start = 12.dp,
                    end = 12.dp,
                    bottom = Constants.BottomNavHeight + 8.dp
                ),
                onSessionClicked = onSessionClicked
            )
        }

    }
}
@Composable
private fun EmptyText(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(
            text = stringResource(R.string.no_statistics),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface.copy(alpha = Constants.ALPHA_GREY)
        )
    }
}

@Composable
private fun HeaderRow(
    onPeriodSelected: (SessionsPeriod) -> Unit,
    currentPeriod: SessionsPeriod,
    modifier: Modifier
) {

    Row(modifier = modifier) {
        Text(
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground,
            text = stringResource(id = R.string.weed_smoked),
            modifier = Modifier.weight(1f)
        )


        ChartFilterDropdown(
            onPeriodSelected = onPeriodSelected,
            currentPeriod = currentPeriod,
            modifier = Modifier.align(CenterVertically)
        )
    }
}

@Composable
private fun StatisticsChart(
    sessionsLineData: LineDataSet,
    modifier: Modifier
) {
    val latoTypeface = ResourcesCompat.getFont(LocalContext.current, R.font.lato_regular)
    val blackColor = MaterialTheme.colors.onBackground.toArgb()
    val primaryColor = MaterialTheme.colors.primary.toArgb()


    AndroidView(
        factory = {
            LineChart(it)
        },
        modifier = modifier,
        update = { lineChart ->
            val lineData = LineData(sessionsLineData.apply {
                color = primaryColor
                valueTextSize = 0f
                mode = LineDataSet.Mode.HORIZONTAL_BEZIER
                setDrawCircleHole(false)
                setDrawCircles(false)
                lineWidth = 2f
            })
            lineChart.apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                axisRight.isEnabled = false
                xAxis.isEnabled = false
                description.isEnabled = false
                legend.isEnabled = false
                isDragEnabled = false
                data = lineData
                setTouchEnabled(false)
                setScaleEnabled(false)
                setPinchZoom(false)
                setDrawGridBackground(false)
                invalidate()
            }

            lineChart.axisLeft.apply {
                axisMinimum = 0f
                typeface = latoTypeface
                textColor = blackColor
                textSize = 12f
            }
        }
    )
}

@Composable
private fun SessionsInfoRow(
    modifier: Modifier = Modifier,
    moneySpent: Double,
    onAddSession: () -> Unit
) {
    Row(modifier = modifier) {
        ClickableText(
            text = AnnotatedString(stringResource(R.string.add_session)),
            onClick = { onAddSession() },
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.primary)
        )

        val annotatedString: AnnotatedString = AnnotatedString.Builder().apply {
            append("${stringResource(R.string.money_spent)}  ")
            withStyle(SpanStyle(MaterialTheme.colors.primary)) {
                append("$moneySpent$")
            }
        }.toAnnotatedString()

        Text(text = annotatedString)
    }
}

@Composable
private fun SessionList(
    modifier: Modifier = Modifier,
    sessionList: List<Session>,
    onSessionClicked: (Session) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(sessionList) { session ->
            SessionItem(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                session = session,
                onClick = onSessionClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SessionItem(
    modifier: Modifier = Modifier,
    session: Session,
    onClick: (Session) -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        elevation = 2.dp,
        onClick = { onClick(session) }
    ) {
        Row(verticalAlignment = CenterVertically) {
            val strainImageUrl = session.strainInfo.imageUrl
            if (strainImageUrl.isNotBlank()) {
                val shape = RoundedCornerShape(16.dp)
                Image(
                    painter = rememberAsyncImagePainter(strainImageUrl),
                    contentDescription = "Strain image",
                    modifier = Modifier
                        .padding(10.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colors.secondary.copy(alpha = Constants.ALPHA_GREY),
                            shape
                        )
                        .clip(shape)
                        .size(55.dp)

                )
            }
            val columnModifier = if (strainImageUrl.isNotBlank()) {
                Modifier.padding(top = 12.dp, start = 10.dp)
            } else {
                Modifier.padding(top = 12.dp, start = 16.dp)
            }
            Column(modifier = columnModifier.fillMaxWidth()) {
                Text(
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1,
                    text = session.strainInfo.title
                )
                val grams = (session.amount * session.amountType.weight)
                val price = (grams * session.pricePerGram)
                val sessionInfo = "${grams.formatZero()}g  -  ${price.formatZero()}$"
                Text(
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                    text = sessionInfo,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Text(
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.body2,
                    text = Time.dayMonthYear(session.timestamp),
                    modifier = Modifier
                        .padding(top = 4.dp, end = 12.dp, bottom = 6.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }


        }
    }
}

@Composable
private fun ChartFilterDropdown(
    onPeriodSelected: (SessionsPeriod) -> Unit,
    currentPeriod: SessionsPeriod,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Box(contentAlignment = Alignment.TopEnd, modifier = modifier) {
        Dropdown(
            currentPeriod = currentPeriod,
            expanded = expanded,
            onPeriodSelected = onPeriodSelected,
            onDismiss = { expanded = false }
        )
        AnimatedVisibility(!expanded) {
            DropdownButton(
                onExpandClicked = { expanded = true },
                currentPeriod = currentPeriod
            )
        }
    }

}

@Composable
private fun Dropdown(
    expanded: Boolean,
    onPeriodSelected: (SessionsPeriod) -> Unit,
    currentPeriod: SessionsPeriod,
    onDismiss: () -> Unit
) {
    MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(8.dp))) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss
        ) {
            val sessionPeriods = SessionsPeriod.values()
            sessionPeriods.forEach { period ->
                DropdownMenuItem(
                    onClick = {
                        onPeriodSelected(period)
                        onDismiss()
                    },
                    modifier = Modifier
                        .height((70 / sessionPeriods.size).dp),
                    contentPadding = PaddingValues(
                        end = 27.dp,
                        start = 15.dp,
                        top = 0.dp,
                        bottom = 0.dp
                    )
                ) {
                    Text(
                        text = stringResource(period.textRes),
                        style = MaterialTheme.typography.body2,
                        color = if (currentPeriod == period) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }

}


@Composable
private fun DropdownButton(
    onExpandClicked: () -> Unit,
    modifier: Modifier = Modifier,
    currentPeriod: SessionsPeriod
) {
    Row(
        modifier = modifier.clickable { onExpandClicked() }
    ) {
        Text(
            text = stringResource(currentPeriod.textRes),
            modifier = Modifier.padding(end = 8.dp),
            style = MaterialTheme.typography.body2
        )
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_down),
            contentDescription = "Show filter",
            modifier = Modifier.align(CenterVertically)
        )
    }

}



