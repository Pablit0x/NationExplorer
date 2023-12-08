package com.pscode.app.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes
import com.pscode.app.presentation.screens.countries.flag_game.QuizButtonState

@Composable
fun QuizButton(
    showButton: Boolean,
    quizButtonState: QuizButtonState,
    navigateHome: () -> Unit,
    modifier: Modifier = Modifier
) {

    AnimatedVisibility(visible = showButton, enter = fadeIn(), exit = fadeOut()) {
        when (quizButtonState) {
            is QuizButtonState.NEXT -> {
                OutlinedButton(onClick = { quizButtonState.onNextClick() }) {
                    Text(text = SharedRes.string.next)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.Default.NavigateNext, contentDescription = "Next flag")
                }
            }

            is QuizButtonState.FINISH -> {
                Button(onClick = { quizButtonState.onFinishClick() }) {
                    Text(text = SharedRes.string.finish)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Finish quiz and show score"
                    )
                }
            }

            is QuizButtonState.HOME -> {
                Button(onClick = { navigateHome() }) {
                    Text(text = SharedRes.string.navigate_home)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Navigate home")
                }
            }
        }
    }
}