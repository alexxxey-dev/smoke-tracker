package com.eatmybrain.smoketracker.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.ui.theme.SmokeTrackerTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StyledButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    icon: Painter? = null,
    textColor: Color = MaterialTheme.colors.onSurface
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = text,
                style = MaterialTheme.typography.subtitle1,
                color = textColor,
                modifier = Modifier.padding(
                    end = if (icon != null) 12.dp else 16.dp,
                    start = 16.dp
                ),
                textAlign = TextAlign.Center
            )
            if (icon != null) {
                Icon(
                    painter = icon,
                    contentDescription = "Button icon",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }

        }
    }
}

@Preview
@Composable
private fun StyledButtonPreview() {
    SmokeTrackerTheme {
        Scaffold {
            it.calculateBottomPadding()
            Column {
                StyledButton(
                    text = stringResource(R.string.save),
                    onClick = { },
                    icon = painterResource(R.drawable.ic_save),
                    modifier = Modifier
                        .padding(top = 16.dp, end = 16.dp, bottom = 20.dp)
                        .height(40.dp)

                )

                StyledButton(
                    text = stringResource(R.string.save),
                    onClick = { },
                    icon = null,
                    modifier = Modifier
                        .padding(top = 16.dp, end = 16.dp, bottom = 20.dp)
                        .height(45.dp)

                )
            }

        }
    }

}