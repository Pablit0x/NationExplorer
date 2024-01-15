package com.pscode.app.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes

@Composable
fun ShowMapButton(visible: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    AnimatedVisibility(
        visible = visible, enter = fadeIn(), exit = fadeOut()
    ) {
        ElevatedButton(
            onClick = onClick, modifier = modifier
        ) {
            Icon(imageVector = Icons.Default.Map, contentDescription = "Show on Map")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = SharedRes.string.show_map,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}