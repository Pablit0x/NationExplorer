package com.pscode.app.presentation.screens.countries.detail

import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.domain.model.LocationOverview
import com.pscode.app.domain.model.WeatherOverview
import com.pscode.app.domain.repository.GeoLocationRepository
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
    private val geoLocationRepository: GeoLocationRepository,
    networkConnectivity: NetworkConnectivity
) : ViewModel() {

    val connectivityStatus = networkConnectivity.observeNetworkStatus().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), initialValue = Status.Idle
    )


    private val _cityWeather = MutableStateFlow<WeatherOverview?>(null)
    val cityWeather = _cityWeather.asStateFlow()

    private val errorChannel = Channel<ErrorEvent>()
    val errorEventsChannelFlow = errorChannel.receiveAsFlow()

    private val _showMap = MutableStateFlow(false)
    val showMap = _showMap.asStateFlow()

    private val _geoLocation = MutableStateFlow<LocationOverview?>(null)
    val geoLocation = _geoLocation.asStateFlow()

    private val _didFetchFail = MutableStateFlow(false)
    val didFetchFail = _didFetchFail.asStateFlow()

    fun getGeoLocation(countryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = geoLocationRepository.getGeoLocationByCountry(
                countryName = countryName
            )

            when (result) {
                is Response.Success -> {
                    _geoLocation.update {
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
                        _cityWeather.update {
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
        _cityWeather.update { null }
        _geoLocation.update { null }
    }

}