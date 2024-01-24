package com.pscode.app.presentation.screens.countries.overview

import androidx.compose.ui.text.input.TextFieldValue
import com.pscode.app.domain.model.CountryData
import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.presentation.screens.shared.Event
import com.pscode.app.utils.Constants
import com.pscode.app.utils.Constants.Continents
import com.pscode.app.utils.Constants.Populations
import com.pscode.app.utils.Response
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OverviewViewModel(private val countryRepository: CountryRepository) : ViewModel() {

    private val _continentSelectableItems = MutableStateFlow(Continents.map {
        SelectableItem(label = it, isSelected = false)
    })

    val continentFilterItems = _continentSelectableItems.asStateFlow()

    private val _populationSelectableItems = MutableStateFlow(Populations.map {
        SelectableItem(label = it.toString(), isSelected = false)
    })

    val populationFilterItems = _populationSelectableItems.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    private val _showFavouritesOnly = MutableStateFlow(false)
    val showFavouritesOnly = _showFavouritesOnly.asStateFlow()

    val isFiltering = combine(_continentSelectableItems.map { continentFilterItems ->
        continentFilterItems.any { it.isSelected }
    }, showFavouritesOnly, populationFilterItems.map { populationFilterItems ->
        populationFilterItems.any { it.isSelected }
    }) { continentFilterResult, favouritesOnlyValue, populationFilterResult ->
        continentFilterResult || favouritesOnlyValue || populationFilterResult
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)


    private val _searchText = MutableStateFlow(TextFieldValue())
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchWidgetState = MutableStateFlow(SearchWidgetState.CLOSED)
    val searchWidgetState = _searchWidgetState.asStateFlow()

    private val _filterWidgetState = MutableStateFlow(FilterWidgetState.CLOSED)
    val filterWidgetState = _filterWidgetState.asStateFlow()


    private var _countries = MutableStateFlow(emptyList<CountryData>())

    val countries = searchText.onEach { _isSearching.update { true } }
        .combine(_countries) { textFieldValue, countries ->
            if (textFieldValue.text.isBlank()) {
                countries
            } else {
                countries.filter {
                    it.name.startsWith(
                        prefix = textFieldValue.text, ignoreCase = true
                    )
                }
            }
        }.combine(_continentSelectableItems) { countries, filterItems ->
            if (filterItems.any { it.isSelected }) {
                val selectedContinents = filterItems.filter { it.isSelected }.map { it.label }

                countries.filter { country ->
                    country.continents.any { continent ->
                        selectedContinents.contains(continent)
                    }
                }
            } else {
                countries
            }
        }.combine(_showFavouritesOnly) { countries, favouritesOnly ->
            if (favouritesOnly) {
                countries.filter { country ->
                    country.isFavourite
                }
            } else {
                countries
            }
        }.combine(_populationSelectableItems) { countries, populationItems ->
            val selectedPopulation = populationItems.firstOrNull { it.isSelected }
            if (selectedPopulation != null) {
                countries.filter {
                    it.population >= selectedPopulation.label.toInt()
                }
            } else {
                countries
            }
        }.onEach { _isSearching.update { false } }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), _countries.value
        )


    private val _eventsChannel = Channel<Event>()
    val eventsChannel = _eventsChannel.receiveAsFlow()


    init {
        loginToRealm()
    }

    fun updateSearchText(text: String) {
        _searchText.update {
            it.copy(
                text = text
            )
        }
    }

    fun toggleFavouriteOnly() {
        _showFavouritesOnly.update { !it }
    }

    fun updateSearchWidgetState(newState: SearchWidgetState) {
        _searchWidgetState.update { newState }
    }

    fun updateFilterWidgetState(newState: FilterWidgetState) {
        _filterWidgetState.update { newState }
    }

    fun updateContinentFilterItem(label: String) {
        val updatedList = _continentSelectableItems.value.map { filterItem ->
            if (filterItem.label == label) {
                filterItem.copy(isSelected = !filterItem.isSelected)
            } else {
                filterItem
            }
        }
        _continentSelectableItems.value = updatedList
    }

    fun updatePopulationFilterItem(label: String) {
        _populationSelectableItems.update {
            it.map { populationFilterItem ->
                if (label != populationFilterItem.label) {
                    populationFilterItem.copy(
                        isSelected = false
                    )
                } else {
                    populationFilterItem.copy(
                        isSelected = !populationFilterItem.isSelected
                    )
                }
            }
        }
    }


    fun resetAllFilters() {
        _continentSelectableItems.update { items ->
            items.map { item ->
                item.copy(isSelected = false)
            }
        }
        _showFavouritesOnly.update { false }

        _populationSelectableItems.update { items ->
            items.map { item ->
                item.copy(isSelected = false)
            }
        }
    }


    fun getAllCountries() {

        _isLoading.update {
            true
        }

        viewModelScope.launch(Dispatchers.IO) {
            when (val result = countryRepository.getAllCountries()) {
                is Response.Success -> {
                    _isLoading.update {
                        false
                    }
                    _countries.update {
                        result.data.filterNot { it.name == "Antarctica" }
                    }
                }

                is Response.Error -> {
                    _eventsChannel.send(Event.ShowSnackbarMessage(message = result.message))

                    _isLoading.update { false }
                }
            }
        }
    }

    private fun loginToRealm() {
        viewModelScope.launch(Dispatchers.IO) {
            App.create(Constants.APP_ID)
                .login(credentials = Credentials.anonymous(reuseExisting = true))
        }
    }
}