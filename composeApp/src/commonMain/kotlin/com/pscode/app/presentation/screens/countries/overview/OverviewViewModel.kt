package com.pscode.app.presentation.screens.countries.overview

import androidx.compose.ui.text.input.TextFieldValue
import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.presentation.screens.shared.ErrorEvent
import com.pscode.app.utils.Constants
import com.pscode.app.utils.Constants.Continents
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

    private val _filterItems = MutableStateFlow(Continents.map {
        FilterItem(label = it, isSelected = false)
    })

    val filterItems = _filterItems.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    private val _favouritesOnly = MutableStateFlow(false)
    val favouritesOnly = _favouritesOnly.asStateFlow()

    val isFiltering = combine(
        _filterItems.map { filterList ->
            filterList.any { it.isSelected }
        }, favouritesOnly
    ) { filterResult, favouritesOnlyValue ->
        filterResult || favouritesOnlyValue
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)


    private val _searchText = MutableStateFlow(TextFieldValue())
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchWidgetState = MutableStateFlow(SearchWidgetState.CLOSED)
    val searchWidgetState = _searchWidgetState.asStateFlow()

    private val _filterWidgetState = MutableStateFlow(FilterWidgetState.CLOSED)
    val filterWidgetState = _filterWidgetState.asStateFlow()

    private val _selectedCountry = MutableStateFlow<CountryOverview?>(null)
    val selectedCountry = _selectedCountry.asStateFlow()

    private val _isFavourite = MutableStateFlow(false)
    val isFavourite = _isFavourite.asStateFlow()

    private val _populationSliderPosition = MutableStateFlow(Constants.POPULATION_RANGE)
    val populationSliderPosition = _populationSliderPosition

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
        }.combine(_filterItems) { countries, filterItems ->
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
        }.combine(_favouritesOnly) { countries, favouritesOnly ->
            if (favouritesOnly) {
                countries.filter { country ->
                    country.isFavourite
                }
            } else {
                countries
            }
        }.onEach { _isSearching.update { false } }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), _countries.value
        )


    private val errorChannel = Channel<ErrorEvent>()
    val errorEventsChannelFlow = errorChannel.receiveAsFlow()


    init {
        getAllCountries()
        loginToRealm()
    }

    fun onPopulationSliderPositionChange(updatedPosition: ClosedFloatingPointRange<Float>) {
        _populationSliderPosition.update { updatedPosition }
    }

    fun onSearchTextChange(text: String) {
        _searchText.update {
            it.copy(
                text = text
            )
        }
    }

    fun setIsFavourite(isFavourite: Boolean) {
        _isFavourite.update { isFavourite }
    }


    fun onFavouriteOnlySwitchToggle() {
        _favouritesOnly.update { !it }
    }


    fun toggleFavourite(countryName: CountryOverview?) {
        viewModelScope.launch {
            countryRepository.toggleFavourites(country = countryName).let { response ->
                when (response) {
                    is Response.Success -> {
                        _countries.update { response.data }
                        _isFavourite.update { !it }
                    }

                    is Response.Error -> {

                    }
                }
            }
        }
    }

    fun onSearchWidgetChange(newState: SearchWidgetState) {
        _searchWidgetState.update { newState }
    }

    fun onFilterWidgetStateChange(newState: FilterWidgetState) {
        _filterWidgetState.update { newState }
    }

    fun setSelectedCountry(country: CountryOverview?) {
        _selectedCountry.update {
            country
        }
    }

    fun updateFilterItemSelected(label: String) {
        val updatedList = _filterItems.value.map { filterItem ->
            if (filterItem.label == label) {
                filterItem.copy(isSelected = !filterItem.isSelected)
            } else {
                filterItem
            }
        }
        _filterItems.value = updatedList
    }

    fun clearAllFilters() {
        _filterItems.update { items ->
            items.map { item ->
                item.copy(isSelected = false)
            }
        }
        _favouritesOnly.update { false }
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
                    errorChannel.send(ErrorEvent.ShowSnackbarMessage(message = result.message))

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