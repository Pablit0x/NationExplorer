package com.pscode.app.presentation.composables

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomLinearProgressIndicator(currentRound: Int, modifier: Modifier = Modifier) {

    val animatedProgress by animateFloatAsState(
        targetValue = currentRound / 10f, animationSpec = TweenSpec(durationMillis = 500)
    )


    Column(modifier = modifier) {
        Text(
            text = "$currentRound/10",
            modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
            color = MaterialTheme.colorScheme.outline
        )
        LinearProgressIndicator(progress = animatedProgress, modifier = Modifier.fillMaxWidth())
    }
}