package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun CountryListItem(
    countryName: String, flagUrl: String, modifier: Modifier = Modifier
) {

    OutlinedCard(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            KamelImage(
                resource = asyncPainterResource(flagUrl),
                contentDescription = "Country flag",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(width = 100.dp, height = 60.dp)
            )

            Text(text = countryName, style = MaterialTheme.typography.headlineMedium)
        }
    }
}