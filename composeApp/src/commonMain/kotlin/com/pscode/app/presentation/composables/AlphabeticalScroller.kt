package com.pscode.app.presentation.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AlphabeticalScroller(
    onLetterClick: (Char) -> Unit, modifier: Modifier = Modifier
) {
    val alphabet = ('A'..'Z')
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        alphabet.forEach { letter ->
            Text(
                text = letter.uppercase(),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.noRippleClickable {
                    onLetterClick(letter)
                }.padding(vertical = 1.dp)
            )
        }
    }
}

