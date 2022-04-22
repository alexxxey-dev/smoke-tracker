package com.eatmybrain.smoketracker.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.LocalOverScrollConfiguration
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp



private val LightColorPalette = lightColors(
    primary = Green,
    background = SoftWhite,
    surface = White,
    secondary = Grey,
    onBackground = SoftBlack,
    onSurface = SoftBlack
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SmokeTrackerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes
    ) {
        CompositionLocalProvider(
            LocalOverScrollConfiguration provides null,
            content = content
        )
    }
}