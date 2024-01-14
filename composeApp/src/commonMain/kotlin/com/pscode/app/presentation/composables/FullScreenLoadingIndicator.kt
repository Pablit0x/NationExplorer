package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FullScreenLoadingIndicator(
    onNavigateBackOnDrag: () -> Unit = {}, innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(innerPadding)
            .navigateBackOnDrag(onNavigateBack = { onNavigateBackOnDrag() }),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}