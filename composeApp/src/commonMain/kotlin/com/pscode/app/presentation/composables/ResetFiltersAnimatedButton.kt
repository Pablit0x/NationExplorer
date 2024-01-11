package com.pscode.app.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes

@Composable
fun ResetFiltersAnimatedButton(
    isFiltering: Boolean,
    showFavouritesOnly: Boolean,
    onResetAllFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(
            isFiltering or showFavouritesOnly, enter = fadeIn(tween(1500)), exit = fadeOut()
        ) {
            ElevatedButton(
                onClick = onResetAllFilters, modifier = Modifier.padding(16.dp).fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = "Clear all filters"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = SharedRes.string.reset_filters, color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}