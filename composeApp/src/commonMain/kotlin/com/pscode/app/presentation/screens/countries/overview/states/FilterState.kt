package com.pscode.app.presentation.screens.countries.overview.states

import com.pscode.app.domain.model.SelectableItem
import com.pscode.app.utils.Constants

data class FilterState(
    val populationItems : List<SelectableItem> = Constants.Populations.map {
        SelectableItem(label = it.toString(), isSelected = false)
    },
    val continentItems: List<SelectableItem> = Constants.Continents.map {
        SelectableItem(label = it, isSelected = false)
    },
    val showFavouritesOnly : Boolean = false,
    val isFiltering: Boolean = false,
    val widgetState: WidgetState = WidgetState.CLOSED
)


