package com.pscode.app.presentation.screens.countries.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes
import com.pscode.app.presentation.composables.AutoResizedText
import com.pscode.app.presentation.composables.ContinentFilterSection
import com.pscode.app.presentation.composables.PopulationFilterSection
import com.pscode.app.presentation.composables.ResetFiltersAnimatedButton
import com.pscode.app.presentation.composables.ShowOnlyFavouritesFilterSection

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

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            AutoResizedText(
                text = SharedRes.string.filter_results,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth()
            )

            PopulationFilterSection(
                populationFilterItems = populationFilterItems,
                onPopulationFilterItemClick = onUpdatePopulationFilterItem,
                modifier = Modifier.fillMaxWidth()
            )

            ContinentFilterSection(
                continentsFilterItems = continentsFilterItems,
                onContinentFilterItemClick = onUpdateContinentFilterItem,
                modifier = Modifier.fillMaxWidth()
            )

            Divider()

            ShowOnlyFavouritesFilterSection(
                showFavouritesOnly = showFavouritesOnly,
                onToggleFavouriteOnly = onToggleFavouriteOnly,
                modifier = Modifier.fillMaxWidth()
            )

            ResetFiltersAnimatedButton(
                isFiltering = isFiltering,
                showFavouritesOnly = showFavouritesOnly,
                onResetAllFilters = onResetAllFilters,
                modifier = Modifier.height(90.dp)
            )
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
