package com.pscode.app.presentation.screens.countries.overview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes
import com.pscode.app.presentation.composables.AutoResizedText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    continentsFilterItems: List<FilterItem>,
    populationFilterItems: List<FilterItem>,
    isFiltering: Boolean,
    showFavouritesOnly: Boolean,
    sheetState: SheetState,
    onUpdateFilterWidgetState: (FilterWidgetState) -> Unit,
    onUpdateContinentFilterItem: (String) -> Unit,
    onUpdatePopulationFilterItem: (String) -> Unit,
    onToggleFavouriteOnly: () -> Unit,
    onResetAllFilters: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = { onUpdateFilterWidgetState(FilterWidgetState.CLOSED) }) {

        AutoResizedText(
            text = SharedRes.string.filter_results,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = SharedRes.string.population,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            populationFilterItems.forEach { populationItem ->
                FilterChip(

                    selected = populationItem.isSelected,
                    label = {
                        Text(
                            text = "< " + convertPopulation(populationItem.label.toLong()),
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    onClick = { onUpdatePopulationFilterItem(populationItem.label) },
                    modifier = Modifier.padding(4.dp).weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = SharedRes.string.continents,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(count = 3),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalItemSpacing = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(continentsFilterItems) { continent ->
                ElevatedFilterChip(selected = continent.isSelected,
                    onClick = { onUpdateContinentFilterItem(continent.label) },
                    label = {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            AnimatedVisibility(continent.isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Filter Applied",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(
                                text = continent.label, style = MaterialTheme.typography.labelSmall
                            )
                        }
                    })
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Divider(modifier = Modifier.padding(horizontal = 16.dp))

        Spacer(modifier = Modifier.height(8.dp))


        Row(
            modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)
                .padding(horizontal = 16.dp),
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


        Column(
            modifier = Modifier.fillMaxWidth().height(90.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            AnimatedVisibility(
                isFiltering or showFavouritesOnly, enter = fadeIn(), exit = fadeOut()
            ) {
                ElevatedButton(
                    onClick = onResetAllFilters, modifier = Modifier.padding(
                        top = 32.dp, bottom = 16.dp, start = 32.dp, end = 32.dp
                    ).fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.error,
                        contentDescription = "Clear all filters"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Reset Filters", color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

fun convertPopulation(number: Long): String {
    return when {
        number < 1000L -> number.toString()
        number in 1000L until 1_000_000L -> "${number / 1000L}K"
        number in 1_000_000L until 1_000_000_000L -> "${number / 1_000_000L}M"
        else -> "${number / 1_000_000L}M"
    }
}
