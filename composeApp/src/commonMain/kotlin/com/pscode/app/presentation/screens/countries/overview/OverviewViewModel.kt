package com.pscode.app.presentation.screens.countries.overview

import androidx.compose.ui.text.input.TextFieldValue
import com.pscode.app.domain.model.CountryOverview
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

    private val _continentFilterItems = MutableStateFlow(Continents.map {
        FilterItem(label = it, isSelected = false)
    })

    val continentFilterItems = _continentFilterItems.asStateFlow()

    private val _populationFilterItems = MutableStateFlow(Populations.map {
        FilterItem(label = it.toString(), isSelected = false)
    })

    val populationFilterItems = _populationFilterItems.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    private val _showFavouritesOnly = MutableStateFlow(false)
    val showFavouritesOnly = _showFavouritesOnly.asStateFlow()

    val isFiltering = combine(_continentFilterItems.map { continentFilterItems ->
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

    private val _selectedCountryOverview = MutableStateFlow<CountryOverview?>(null)
    val selectedCountryOverview = _selectedCountryOverview.asStateFlow()

    private val _isCountryFavourite = MutableStateFlow(false)
    val isCountryFavourite = _isCountryFavourite.asStateFlow()


    private var _countries = MutableStateFlow(emptyList<CountryOverview>())

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
        }.combine(_continentFilterItems) { countries, filterItems ->
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
        }.combine(_populationFilterItems) { countries, populationItems ->
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


    private val _eventChannel = Channel<Event>()
    val eventChannel = _eventChannel.receiveAsFlow()


    init {
        getAllCountries()
        loginToRealm()
    }

    fun updateSearchText(text: String) {
        _searchText.update {
            it.copy(
                text = text
            )
        }
    }

    fun setFavouriteStatus(isFavourite: Boolean) {
        _isCountryFavourite.update { isFavourite }
    }


    fun toggleFavouriteOnly() {
        _showFavouritesOnly.update { !it }
    }


    fun toggleCountryFavourite(countryName: CountryOverview?) {
        viewModelScope.launch {
            countryRepository.toggleFavourites(country = countryName).let { response ->
                when (response) {
                    is Response.Success -> {
                        _countries.update { response.data }
                        _isCountryFavourite.update { !it }
                    }

                    is Response.Error -> {

                    }
                }
            }
        }
    }

    fun updateSearchWidgetState(newState: SearchWidgetState) {
        _searchWidgetState.update { newState }
    }

    fun updateFilterWidgetState(newState: FilterWidgetState) {
        _filterWidgetState.update { newState }
    }

    fun setSelectedCountryOverview(country: CountryOverview?) {
        _selectedCountryOverview.update {
            country
        }
    }

    fun updateContinentFilterItem(label: String) {
        val updatedList = _continentFilterItems.value.map { filterItem ->
            if (filterItem.label == label) {
                filterItem.copy(isSelected = !filterItem.isSelected)
            } else {
                filterItem
            }
        }
        _continentFilterItems.value = updatedList
    }

    fun updatePopulationFilterItem(label: String) {
        _populationFilterItems.update {
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
        _continentFilterItems.update { items ->
            items.map { item ->
                item.copy(isSelected = false)
            }
        }
        _showFavouritesOnly.update { false }

        _populationFilterItems.update { items ->
            items.map { item ->
                item.copy(isSelected = false)
            }
        }
    }


    private fun getAllCountries() {

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
                        result.data
                    }
                }

                is Response.Error -> {
                    _eventChannel.send(Event.ShowSnackbarMessage(message = result.message))

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