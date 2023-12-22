package com.pscode.app.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.pscode.app.domain.model.CountryOverview
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun CountryListItem(
    countryOverview: CountryOverview,
    onCountryClick: (CountryOverview) -> Unit,
    modifier: Modifier = Modifier
) {

    OutlinedCard(modifier = modifier
        .clickable {
            onCountryClick(countryOverview)
        }) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            KamelImage(
                resource = asyncPainterResource(countryOverview.flagUrl),
                contentDescription = "Country flag",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(width = 100.dp, height = 60.dp)
            )

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                AutoResizedText(
                    text = countryOverview.name,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}