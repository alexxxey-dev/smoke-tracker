package com.eatmybrain.smoketracker.ui.components

import android.view.textservice.TextInfo
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
 fun CircleProgressBar(radius: Dp, strokeWidth: Dp, percentage: Float) {
    val grey = MaterialTheme.colors.secondaryVariant
    val primary = MaterialTheme.colors.primary
    val primaryVariant = MaterialTheme.colors.primaryVariant
    Canvas(Modifier.size(radius * 2f)) {
        drawCircle(
            SolidColor(grey),
            radius = size.width / 2,
            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
        )

        drawArc(
            brush = Brush.sweepGradient(listOf(
                primaryVariant,
                primaryVariant
            )),
            startAngle = -90f,
            sweepAngle = 360f * percentage,
            useCenter = false,
            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
        )
    }
}

