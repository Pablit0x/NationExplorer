package com.pscode.app.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowOnMapCard(
    visible: Boolean, onClick: () -> Unit, modifier: Modifier
) {
    AnimatedVisibility(
        visible = visible, enter = slideInHorizontally(), exit = slideOutHorizontally()
    ) {


        ElevatedCard(modifier = modifier, shape = RoundedCornerShape(10), onClick = onClick) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Map, contentDescription = "Show on Map")
                    Text(
                        text = SharedRes.string.show_map,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}