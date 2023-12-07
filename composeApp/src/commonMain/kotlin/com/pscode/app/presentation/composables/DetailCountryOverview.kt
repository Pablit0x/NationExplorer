package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes
import com.pscode.app.domain.model.CountryOverview
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun DetailCountryOverview(
    selectedCountry: CountryOverview, hasCapitalCity: Boolean, modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        KamelImage(
            resource = asyncPainterResource(selectedCountry.flagUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(width = 200.dp, height = 120.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = SharedRes.string.information,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        OutlinedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (selectedCountry.capitals.size >= 2) "Capitals: " else "Capital: " + if (hasCapitalCity) selectedCountry.capitals.joinToString(
                    separator = ", "
                ) else "No capital city",
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            )
            Text(
                text = "Area: ${selectedCountry.area} km²",
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            )
            Text(
                text = "Population: ${selectedCountry.population}",
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            )
            Text(
                text = "Timezones: ${selectedCountry.timezones.joinToString(separator = ", ")}",
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}