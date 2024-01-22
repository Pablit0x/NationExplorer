package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun WeatherItem(
    iconVector: ImageVector,
    contentDescription: String,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {

    AnimatedBorderCard(
        modifier = modifier,
        shape = RoundedCornerShape(percent = 10),
        borderWidth = 1.dp,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = iconVector,
                    contentDescription = contentDescription,
                    modifier = Modifier.size(36.dp).padding(4.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = value,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun WeatherItemShimmer(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.height(48.dp).clip(shape = RoundedCornerShape(percent = 15))
            .shimmerEffect()
    )
}