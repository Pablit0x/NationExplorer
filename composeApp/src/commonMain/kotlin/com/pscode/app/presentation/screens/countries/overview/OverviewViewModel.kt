package com.pscode.app.presentation.screens.countries.overview

import androidx.compose.ui.text.input.TextFieldValue
import com.pscode.app.domain.model.CountryData
import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.presentation.screens.countries.overview.states.FilterState
import com.pscode.app.presentation.screens.countries.overview.states.SearchState
import com.pscode.app.presentation.screens.countries.overview.states.WidgetState
import com.pscode.app.presentation.screens.shared.Event
import com.pscode.app.utils.Constants
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

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _filterState = MutableStateFlow(FilterState())

    val filterState = _filterState.map { state ->
        FilterState(populationItems = state.populationItems,
            continentItems = state.continentItems,
            showFavouritesOnly = state.showFavouritesOnly,
            widgetState = state.widgetState,
            isFiltering = state.showFavouritesOnly || state.continentItems.any { it.isSelected } || state.populationItems.any { it.isSelected })
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _filterState.value)

    private val _searchState = MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()


    private val _searchText = MutableStateFlow(TextFieldValue())
    val searchText = _searchText.asStateFlow()


    private var _countries = MutableStateFlow(emptyList<CountryData>())

    val countries = searchText.onEach {
        _searchState.update {
            it.copy(
                isSearching = true
            )
        }
    }.combine(_countries) { textFieldValue, countries ->
        if (textFieldValue.text.isBlank()) {
            countries
        } else {
            countries.filter {
                it.name.startsWith(
                    prefix = textFieldValue.text, ignoreCase = true
                )
            }
        }
    }.combine(_filterState) { countries, state ->
        if (state.continentItems.any { it.isSelected }) {
            val selectedContinents = state.continentItems.filter { it.isSelected }.map { it.label }

            countries.filter { country ->
                country.continents.any { continent ->
                    selectedContinents.contains(continent)
                }
            }
        } else {
            countries
        }
    }.combine(_filterState) { countries, state ->
        if (state.showFavouritesOnly) {
            countries.filter { country ->
                country.isFavourite
            }
        } else {
            countries
        }
    }.combine(_filterState) { countries, state ->
        val selectedPopulation = state.populationItems.firstOrNull { it.isSelected }
        if (selectedPopulation != null) {
            countries.filter {
                it.population >= selectedPopulation.label.toInt()
            }
        } else {
            countries
        }
    }.onEach {
        _searchState.update {
            it.copy(
                isSearching = false
            )
        }
    }.stateIn(
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
        _filterState.update {
            it.copy(
                showFavouritesOnly = !it.showFavouritesOnly
            )
        }
    }

    fun updateSearchWidgetState(newState: WidgetState) {
        _searchState.update {
            it.copy(
                widgetState = newState
            )
        }
    }

    fun updateFilterWidgetState(newState: WidgetState) {
        _filterState.update {
            it.copy(
                widgetState = newState
            )
        }
    }

    fun updateContinentFilterItem(label: String) {
        _filterState.update {
            it.copy(continentItems = it.continentItems.map { continent ->
                if (continent.label == label) {
                    continent.copy(isSelected = !continent.isSelected)
                } else {
                    continent
                }
            })
        }
    }

    fun updatePopulationFilterItem(label: String) {
        _filterState.update {
            it.copy(populationItems = it.populationItems.map { populationFilterItem ->
                if (label != populationFilterItem.label) {
                    populationFilterItem.copy(
                        isSelected = false
                    )
                } else {
                    populationFilterItem.copy(
                        isSelected = !populationFilterItem.isSelected
                    )
                }

            })
        }
    }


    fun resetAllFilters() {
        _filterState.update {
            FilterState()
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