package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.People
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

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = SharedRes.string.information,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(2.dp))

        OutlinedCard(
            modifier = Modifier.fillMaxWidth()
        ) {

            DetailCountryInformationItem(
                icon = Icons.Default.LocationCity,
                iconDescription = "Capital Icon",
                key = SharedRes.string.capital.format(selectedCountry.capitals.size),
                value = if (hasCapitalCity) selectedCountry.capitals.joinToString(
                    separator = ", "
                ) else SharedRes.string.no_capital_city,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            )


            DetailCountryInformationItem(
                icon = Icons.Default.Layers,
                iconDescription = "Area Icon",
                key = SharedRes.string.area,
                value = selectedCountry.area.toString(),
                valueAppendix = " km²",
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 8.dp)
            )


            DetailCountryInformationItem(
                icon = Icons.Default.People,
                iconDescription = "Population Icon",
                key = SharedRes.string.population,
                value = formatNumber(number = selectedCountry.population),
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 8.dp)
            )

            DetailCountryInformationItem(
                icon = Icons.Default.Alarm,
                iconDescription = "Timezone Icon",
                key = SharedRes.string.timezone.format(selectedCountry.timezones.size),
                value = selectedCountry.timezones.joinToString(separator = ", "),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            )
        }
    }
}

fun formatNumber(number: Int): String {
    return number.toString().reversed().chunked(3).joinToString(" ").reversed()
}