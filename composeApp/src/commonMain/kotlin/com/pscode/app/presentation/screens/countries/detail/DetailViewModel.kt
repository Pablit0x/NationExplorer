package com.pscode.app.presentation.screens.countries.detail

import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.domain.model.LocationOverview
import com.pscode.app.domain.model.TidbitOverview
import com.pscode.app.domain.model.WeatherOverview
import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.domain.repository.GeolocationRepository
import com.pscode.app.domain.repository.TidbitsRepository
import com.pscode.app.domain.repository.WeatherRepository
import com.pscode.app.presentation.screens.shared.Event
import com.pscode.app.utils.NetworkConnectivity
import com.pscode.app.utils.Response
import com.pscode.app.utils.Status
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val weatherRepository: WeatherRepository,
    private val geolocationRepository: GeolocationRepository,
    private val tidbitsRepository: TidbitsRepository,
    private val countryRepository: CountryRepository,
    networkConnectivity: NetworkConnectivity
) : ViewModel() {

    val connectivityStatus = networkConnectivity.observeNetworkStatus().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), initialValue = Status.Idle
    )

    private val _isCountryFavourite = MutableStateFlow(false)
    val isCountryFavourite = _isCountryFavourite.asStateFlow()

    private val _currentWeather = MutableStateFlow<WeatherOverview?>(null)
    val currentWeather = _currentWeather.asStateFlow()

    private val _eventsChannel = Channel<Event>()
    val eventsChannel = _eventsChannel.receiveAsFlow()

    private val _isMapVisible = MutableStateFlow(false)
    val isMapVisible = _isMapVisible.asStateFlow()

    private val _countryGeolocation = MutableStateFlow<LocationOverview?>(null)
    val countryGeolocation = _countryGeolocation.asStateFlow()

    private val _tidbitsList = MutableStateFlow<List<TidbitOverview>>(emptyList())
    val tidbitsList = _tidbitsList.asStateFlow()

    private val _currentTidbitId = MutableStateFlow(0)
    val currentTidbitId = _currentTidbitId.asStateFlow()

    private val _didFetchFail = MutableStateFlow(false)
    val didFetchFail = _didFetchFail.asStateFlow()
    fun getTidbitsByCountry(countryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = tidbitsRepository.getTidbitsByCountryName(countryName = countryName)
            when (result) {
                is Response.Success -> {
                    _tidbitsList.update {
                        result.data
                    }
                }

                is Response.Error -> {

                }
            }
        }
    }

    fun setCurrentTidbitId(id: Int) {
        _currentTidbitId.update {
            id
        }
    }


    fun setFavouriteStatus(isFavourite: Boolean) {
        _isCountryFavourite.update { isFavourite }
    }


    fun toggleCountryFavourite(country: CountryOverview?) {
        viewModelScope.launch {
            countryRepository.toggleFavourites(country = country).let { response ->
                when (response) {
                    is Response.Success -> {
                        _isCountryFavourite.update { !it }
                    }

                    is Response.Error -> {

                    }
                }
            }
        }
    }


    fun getGeolocationByCountry(countryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = geolocationRepository.getGeolocationByCountry(
                countryName = countryName
            )

            when (result) {
                is Response.Success -> {
                    _countryGeolocation.update {
                        result.data
                    }
                }

                is Response.Error -> {
                    _didFetchFail.update { true }
                }
            }
        }
    }

    fun getWeatherByCity(country: CountryOverview) {
        country.capitals.firstOrNull()?.let { countryName ->
            viewModelScope.launch(Dispatchers.IO) {
                val result: Response<WeatherOverview> =
                    weatherRepository.getWeatherByCity(cityName = countryName)

                when (result) {
                    is Response.Success -> {
                        _currentWeather.update {
                            result.data
                        }
                    }

                    is Response.Error -> {
                        _didFetchFail.update { true }
                    }
                }
            }
        }
    }

    fun showMap() {
        _isMapVisible.update { true }
    }

    fun hideMap() {
        _isMapVisible.update { false }
    }

    fun resetViewModel() {
        _isMapVisible.update { false }
        _didFetchFail.update { false }
        _currentWeather.update { null }
        _countryGeolocation.update { null }
        _tidbitsList.update { emptyList() }
    }

}