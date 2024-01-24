package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes
import com.pscode.app.domain.model.SelectableItem
import com.pscode.app.presentation.screens.countries.overview.convertPopulation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopulationFilterSection(
    populationSelectableItems: List<SelectableItem>,
    onPopulationFilterItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        Text(
            text = SharedRes.string.population,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.outline
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            populationSelectableItems.forEach { populationItem ->
                FilterChip(
                    selected = populationItem.isSelected,
                    label = {
                        Text(
                            text = "< " + convertPopulation(populationItem.label.toLong()),
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    onClick = { onPopulationFilterItemClick(populationItem.label) },
                    modifier = Modifier.padding(4.dp).weight(1f)
                )
            }
        }
    }
}