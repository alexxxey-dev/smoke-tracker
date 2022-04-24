package com.eatmybrain.smoketracker.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.util.Constants
import com.eatmybrain.smoketracker.util.Time


@Composable
fun CircleProgressBar(
    timeLeft: Long,
    percentage: Float,
    radius: Dp = 120.dp,
    strokeWidth: Dp = 18.dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ) {
        ProgressBar(radius = radius, strokeWidth = strokeWidth, percentage = percentage)
        TextInfo(timeLeft)
    }
}

@Composable
private fun ProgressBar(radius: Dp, strokeWidth: Dp, percentage: Float) {
    val grey = MaterialTheme.colors.secondaryVariant
    val primary = MaterialTheme.colors.primary
    val primaryVariant = MaterialTheme.colors.primaryVariant
    Canvas(Modifier.size(radius * 2f)) {
        drawCircle(
            SolidColor(grey),
            radius = size.width / 2,
            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
        )

        val convertValue = (percentage / 30) * 360

        drawArc(
            brush = Brush.sweepGradient(listOf(
                primary,
                primary,
                primaryVariant
            )),
            startAngle = -90f,
            sweepAngle = convertValue,
            useCenter = false,
            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun TextInfo(timeLeft: Long) {
    val minutes = Time.millisToMinutes(timeLeft) % 60
    val hours = Time.millisToHours(timeLeft) % 24
    val days = Time.millisToDays(timeLeft)

    Row {
        TextInfoItem(
            title = days.toString(),
            subtitle = stringResource(R.string.days),
            modifier = Modifier.padding(end = 20.dp)
        )

        TextInfoItem(
            title = hours.toString(),
            subtitle = stringResource(R.string.hours),
            modifier = Modifier.padding(end = 20.dp)
        )

        TextInfoItem(
            title = minutes.toString(),
            subtitle = stringResource(R.string.minutes)
        )
    }
}

@Composable
private fun TextInfoItem(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.primary
        )

        Text(
            text = subtitle,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground
        )
    }

}