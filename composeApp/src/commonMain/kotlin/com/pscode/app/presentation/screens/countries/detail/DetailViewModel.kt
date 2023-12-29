package com.pscode.app.presentation.screens.countries.detail

import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.domain.model.LocationOverview
import com.pscode.app.domain.model.TidbitOverview
import com.pscode.app.domain.model.WeatherOverview
import com.pscode.app.domain.repository.GeolocationRepository
import com.pscode.app.domain.repository.TidbitsRepository
import com.pscode.app.domain.repository.WeatherRepository
import com.pscode.app.presentation.screens.shared.ErrorEvent
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
    networkConnectivity: NetworkConnectivity
) : ViewModel() {

    val connectivityStatus = networkConnectivity.observeNetworkStatus().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), initialValue = Status.Idle
    )

    private val _weather = MutableStateFlow<WeatherOverview?>(null)
    val weather = _weather.asStateFlow()

    private val errorChannel = Channel<ErrorEvent>()
    val errorEventsChannelFlow = errorChannel.receiveAsFlow()

    private val _showMap = MutableStateFlow(false)
    val showMap = _showMap.asStateFlow()

    private val _geolocation = MutableStateFlow<LocationOverview?>(null)
    val geolocation = _geolocation.asStateFlow()

    private val _tidbits = MutableStateFlow<List<TidbitOverview>>(emptyList())
    val tidbits = _tidbits.asStateFlow()

    private val _currentTidbitId = MutableStateFlow(0)
    val currentTidbitId = _currentTidbitId.asStateFlow()

    private val _didFetchFail = MutableStateFlow(false)
    val didFetchFail = _didFetchFail.asStateFlow()

    fun getTidbitsByCountry(countryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = tidbitsRepository.getTidbitsByCountryName(countryName = countryName)
            when (result) {
                is Response.Success -> {
                    _tidbits.update {
                        result.data
                    }
                }

                is Response.Error -> {

                }
            }
        }
    }

    fun setCurrentTidbit(id: Int) {
        _currentTidbitId.update {
            id
        }
    }


    fun getGeolocationByCountry(countryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = geolocationRepository.getGeolocationByCountry(
                countryName = countryName
            )

            when (result) {
                is Response.Success -> {
                    _geolocation.update {
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
                        _weather.update {
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
        _showMap.update { true }
    }

    fun hideMap() {
        _showMap.update { false }
    }

    fun clear() {
        _showMap.update { false }
        _didFetchFail.update { false }
        _weather.update { null }
        _geolocation.update { null }
        _tidbits.update { emptyList() }
    }

}