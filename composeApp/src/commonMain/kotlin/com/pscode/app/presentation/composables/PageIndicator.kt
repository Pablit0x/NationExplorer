package com.pscode.app.presentation.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun PageIndicator(
    onClick: (Int) -> Unit,
    numberOfPages: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(numberOfPages) {
            Indicator(
                isCurrentlySelected = currentPage == it,
                onClick = { onClick(it) }
            )
        }
    }
}

@Composable
fun Indicator(
    onClick: () -> Unit,
    isCurrentlySelected: Boolean
) {
    val width = animateDpAsState(
        targetValue = if (isCurrentlySelected) 25.dp else 10.dp, label = "Indicator Dp Animation"
    )

    Box(
        modifier = Modifier
            .padding(1.dp)
            .height(10.dp)
            .width(width.value)
            .clip(Shapes().extraLarge)
            .background(if (isCurrentlySelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.scrim)
            .noRippleClickable { onClick() }
    )
}