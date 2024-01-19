package com.pscode.app.presentation.screens.countries.detail

import com.pscode.app.SharedRes
import com.pscode.app.domain.model.CelebrityOverview
import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.domain.model.LocationOverview
import com.pscode.app.domain.model.TidbitOverview
import com.pscode.app.domain.model.WeatherOverview
import com.pscode.app.domain.model.SixMonthsWeatherOverview
import com.pscode.app.domain.repository.CelebrityRepository
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
    private val celebrityRepository: CelebrityRepository,
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

    private val _celebritiesList = MutableStateFlow<List<CelebrityOverview>>(emptyList())
    val celebritiesList = _celebritiesList.asStateFlow()

    private val _currentTidbitId = MutableStateFlow(0)
    val currentTidbitId = _currentTidbitId.asStateFlow()

    private val _didFetchFail = MutableStateFlow(false)
    val didFetchFail = _didFetchFail.asStateFlow()

    private val _tidbitCardState = MutableStateFlow(CardState.COLLAPSED)
    val tidbitCardState = _tidbitCardState.asStateFlow()

    private val _celebrityCardState = MutableStateFlow(CardState.COLLAPSED)
    val celebrityCardState = _celebrityCardState.asStateFlow()

    private val _yearlyWeatherOverview = MutableStateFlow(
        SixMonthsWeatherOverview(monthAverages = emptyList())
    )
    val sixMonthsWeatherOverview = _yearlyWeatherOverview.asStateFlow()

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

    fun getCelebritiesByCountry(countryName: String) {
        viewModelScope.launch {
            val result = celebrityRepository.getCelebritiesByCountryName(countryName = countryName)
            when (result) {
                is Response.Success -> {
                    _celebritiesList.update {
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


    fun toggleCountryFavourite(country: CountryOverview) {
        viewModelScope.launch {
            countryRepository.toggleFavourites(country = country).let { response ->
                when (response) {
                    is Response.Success -> {
                        _isCountryFavourite.update { currentFavouriteValue ->
                            !currentFavouriteValue
                        }
                        if (isCountryFavourite.value) {
                            _eventsChannel.send(
                                Event.ShowSnackbarMessageWithAction(message = SharedRes.string.country_added_to_favourites.format(
                                    country = country.name
                                ),
                                    actionLabel = SharedRes.string.Undo,
                                    action = { toggleCountryFavourite(country = country) })
                            )
                        }
                    }

                    is Response.Error -> {

                    }
                }
            }
        }
    }

    fun updateTidbitCardState(newState: CardState) {
        _tidbitCardState.update { newState }
    }

    fun updateCelebrityCardState(newState: CardState) {
        _celebrityCardState.update { newState }
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
                    _countryGeolocation.value?.let {
                        getTemperatureRangePastYear(it)
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

    private fun getTemperatureRangePastYear(locationOverview: LocationOverview) {
        viewModelScope.launch {
            val response =
                weatherRepository.getTemperatureRangePastSixMonths(locationOverview = locationOverview)
            when (response) {
                is Response.Success -> {
                    _yearlyWeatherOverview.update { response.data }
                }

                is Response.Error -> {
                    _eventsChannel.send(
                        Event.ShowSnackbarMessage(message = response.message)
                    )
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
        _celebritiesList.update { emptyList() }
        _tidbitCardState.update { CardState.COLLAPSED }
        _celebrityCardState.update { CardState.COLLAPSED }
        _yearlyWeatherOverview.update { SixMonthsWeatherOverview(monthAverages = emptyList()) }
    }

}