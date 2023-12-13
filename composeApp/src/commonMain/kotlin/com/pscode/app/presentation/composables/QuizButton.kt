package com.pscode.app.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes
import com.pscode.app.presentation.screens.countries.flag_game.game.QuizButtonState

@Composable
fun QuizButton(
    showButton: Boolean, quizButtonState: QuizButtonState, modifier: Modifier = Modifier
) {
    AnimatedVisibility(visible = showButton, enter = fadeIn(), exit = ExitTransition.None) {
        when (quizButtonState) {
            is QuizButtonState.NEXT -> {
                Button(modifier = modifier, onClick = { quizButtonState.onNextClick() }) {
                    Text(
                        text = SharedRes.string.next,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.Default.NavigateNext, contentDescription = "Next flag")
                }
            }

            is QuizButtonState.FINISH -> {
                Button(modifier = modifier, onClick = { quizButtonState.onFinishClick() }) {
                    Text(
                        text = SharedRes.string.finish,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Finish quiz and show score"
                    )
                }
            }
        }
    }
}