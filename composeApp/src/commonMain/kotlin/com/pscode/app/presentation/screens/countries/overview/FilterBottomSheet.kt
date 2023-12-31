package com.pscode.app.presentation.screens.countries.overview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SheetState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes
import com.pscode.app.presentation.composables.AutoResizedText
import com.pscode.app.presentation.composables.formatNumber
import com.pscode.app.utils.Constants
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    filterItems: List<FilterItem>,
    isFiltering: Boolean,
    favouritesOnly: Boolean,
    populationSliderPosition: ClosedFloatingPointRange<Float>,
    onSliderPositionChange: (ClosedFloatingPointRange<Float>) -> Unit,
    sheetState: SheetState,
    onFilterWidgetStateChange: (FilterWidgetState) -> Unit,
    onUpdateSelectedFilterItem: (String) -> Unit,
    onFavouriteOnlyToggle: () -> Unit,
    onClearAllFilters: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = { onFilterWidgetStateChange(FilterWidgetState.CLOSED) }) {

        AutoResizedText(
            text = SharedRes.string.filter_results,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = SharedRes.string.population,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.fillMaxWidth(0.3f)
            )

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                Text(
                    text = "${formatNumber(populationSliderPosition.start.roundToInt())} - ${
                        formatNumber(
                            populationSliderPosition.endInclusive.roundToInt()
                        )
                    }",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        RangeSlider(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            value = populationSliderPosition,
            onValueChange = { onSliderPositionChange(it) },
            steps = 1000,
            valueRange = Constants.POPULATION_RANGE
        )

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
            items(filterItems) { continent ->
                ElevatedFilterChip(selected = continent.isSelected,
                    onClick = { onUpdateSelectedFilterItem(continent.label) },
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

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = SharedRes.string.favourites_only,
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.labelLarge
            )
            Switch(checked = favouritesOnly, onCheckedChange = { onFavouriteOnlyToggle() })
        }


        Column(
            modifier = Modifier.fillMaxWidth().height(70.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            AnimatedVisibility(isFiltering or favouritesOnly, enter = fadeIn(), exit = fadeOut()) {
                ElevatedButton(
                    onClick = onClearAllFilters, modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.error,
                        contentDescription = "Clear all filters"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = SharedRes.string.clear, color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
