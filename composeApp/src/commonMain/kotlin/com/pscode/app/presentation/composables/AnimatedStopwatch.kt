package com.pscode.app.presentation.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily

@Composable
fun AnimatedStopwatch(
    timeString: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    fontFamily: FontFamily = FontFamily.Monospace,
    color: Color = MaterialTheme.colorScheme.outlineVariant
) {
    var oldTimeString by remember { mutableStateOf(timeString) }
    SideEffect { oldTimeString = timeString }

    Row(modifier = modifier) {
        timeString.forEachIndexed { index, newChar ->
            if (index < 6 && newChar.isDigit()) { // Animate only if it's a digit and part of minutes or seconds
                val oldChar = oldTimeString.getOrNull(index)
                val char = if (oldChar == newChar) oldTimeString[index] else newChar
                AnimatedContent(
                    targetState = char,
                    transitionSpec = {
                        slideInVertically { it } togetherWith slideOutVertically { -it }
                    }
                ) { digitChar ->
                    Text(
                        text = digitChar.toString(),
                        style = style,
                        color = color,
                        fontFamily = fontFamily,
                        softWrap = false
                    )
                }
            } else {
                // Static text for colons and milliseconds
                Text(
                    text = newChar.toString(),
                    style = style,
                    color = color,
                    fontFamily = fontFamily,
                    softWrap = false
                )
            }
        }
    }
}