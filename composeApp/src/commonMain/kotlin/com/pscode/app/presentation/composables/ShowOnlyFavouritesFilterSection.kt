package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pscode.app.SharedRes

@Composable
fun ShowOnlyFavouritesFilterSection(
    showFavouritesOnly: Boolean, onToggleFavouriteOnly: () -> Unit, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = SharedRes.string.favourites_only,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelLarge
        )
        Switch(checked = showFavouritesOnly, onCheckedChange = { onToggleFavouriteOnly() })
    }
}