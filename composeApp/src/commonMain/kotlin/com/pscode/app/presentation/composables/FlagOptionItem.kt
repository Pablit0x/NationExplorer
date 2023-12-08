package com.pscode.app.presentation.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlagGameOption(
    flagUrl: String,
    isCorrectFlag: Boolean,
    isSelectedFlag: Boolean,
    isCorrectSelection: Boolean,
    isSelectionMade: Boolean,
    onClick: (String) -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = when {
            isCorrectFlag && isSelectionMade -> Color.Green
            isSelectedFlag && !isCorrectSelection && isSelectionMade -> Color.Red
            else -> Color.Transparent
        },
        animationSpec = tween(500)
    )

    OutlinedCard(
        onClick = { if (!isSelectionMade) onClick(flagUrl) },
        border = BorderStroke(width = 4.dp, color = borderColor),
        modifier = Modifier.size(width = 160.dp, height = 130.dp).padding(12.dp)
    ) {
        KamelImage(
            resource = asyncPainterResource(data = flagUrl),
            contentDescription = "Flag option",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize().padding(4.dp)
        )
    }
}

