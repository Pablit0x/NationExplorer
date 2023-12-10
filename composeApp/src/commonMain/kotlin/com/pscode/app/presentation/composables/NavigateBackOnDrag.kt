package com.pscode.app.presentation.composables

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

fun Modifier.navigateBackOnDrag(onNavigateBack: () -> Unit) = composed {
    var totalDragDistance = 0.dp
    val threshold = 360.dp

    this.pointerInput(Unit) {
        detectHorizontalDragGestures { _, dragAmount ->
            totalDragDistance += dragAmount.dp
            if (totalDragDistance > threshold) {
                onNavigateBack()
            }
        }
    }
}