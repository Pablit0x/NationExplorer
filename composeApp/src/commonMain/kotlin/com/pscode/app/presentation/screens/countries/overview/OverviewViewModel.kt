package com.pscode.app.presentation.screens.countries.overview

import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.presentation.screens.shared.ErrorEvent
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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OverviewViewModel(private val countryRepository: CountryRepository) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchWidgetState = MutableStateFlow(SearchWidgetState.CLOSED)
    val searchWidgetState = _searchWidgetState.asStateFlow()

    private val _selectedCountryName = MutableStateFlow<String?>(null)
    val selectedCountryName = _selectedCountryName.asStateFlow()

    private var _countries = MutableStateFlow(emptyList<CountryOverview>())
    val countries =
        searchText.onEach { _isSearching.update { true } }.combine(_countries) { text, countries ->
            if (text.isBlank()) {
                countries
            } else {
                countries.filter {
                    it.name.startsWith(prefix = text, ignoreCase = true)
                }
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

    fun onSearchTextChange(text: String) {
        _searchText.update { text }
    }

    fun onSearchWidgetChange(newState: SearchWidgetState) {
        _searchWidgetState.update { newState }
    }

    fun setSelectedCountryName(countryName: String?) {
        _selectedCountryName.update {
            countryName
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
                    errorChannel.send(ErrorEvent.ShowSnackbarMessage(message = result.message))

                    _isLoading.update { false }
                }
            }
        }
    }

    private fun loginToRealm() {
        viewModelScope.launch(Dispatchers.IO) {
            App.create(Constants.APP_ID).login(credentials = Credentials.anonymous(reuseExisting = true))
        }
    }
}