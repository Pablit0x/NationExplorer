package com.pscode.app.presentation.screens.countries.detail

import com.pscode.app.SharedRes
import com.pscode.app.domain.model.CountryData
import com.pscode.app.domain.model.LocationData
import com.pscode.app.domain.model.SelectableItemWithIcon
import com.pscode.app.domain.model.SixMonthsWeatherData
import com.pscode.app.domain.model.WeatherInfo
import com.pscode.app.domain.repository.CelebrityRepository
import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.domain.repository.GeolocationRepository
import com.pscode.app.domain.repository.TidbitsRepository
import com.pscode.app.domain.repository.WeatherRepository
import com.pscode.app.presentation.screens.countries.detail.states.CardState
import com.pscode.app.presentation.screens.countries.detail.states.CelebrityState
import com.pscode.app.presentation.screens.countries.detail.states.TidbitState
import com.pscode.app.presentation.screens.countries.detail.states.YoutubeVideoState
import com.pscode.app.presentation.screens.shared.Event
import com.pscode.app.utils.Constants
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
import kotlinx.coroutines.flow.combine
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

    private val _eventsChannel = Channel<Event>()
    val eventsChannel = _eventsChannel.receiveAsFlow()

    private val _isMapVisible = MutableStateFlow(false)
    val isMapVisible = _isMapVisible.asStateFlow()

    private val _countryGeolocation = MutableStateFlow<LocationData?>(null)
    val countryGeolocation = _countryGeolocation.asStateFlow()

    private val _tidbitState = MutableStateFlow(TidbitState())
    val tidbitState = _tidbitState.asStateFlow()

    private val _celebrityState = MutableStateFlow(CelebrityState())
    val celebrityState = _celebrityState.asStateFlow()

    private val _youtubeVideoState = MutableStateFlow(YoutubeVideoState())
    val youtubeVideoState = _youtubeVideoState.asStateFlow()

    private val _sixMonthsTemperatureAverage = MutableStateFlow(SixMonthsWeatherData())
    private val _sixMonthsWindSpeedAverage = MutableStateFlow(SixMonthsWeatherData())
    private val _sixMonthsDayLightAverageInHours = MutableStateFlow(SixMonthsWeatherData())
    private val _sixMonthsRainSumInMm = MutableStateFlow(SixMonthsWeatherData())

    val weatherAverages = combine(
        _sixMonthsTemperatureAverage,
        _sixMonthsWindSpeedAverage,
        _sixMonthsDayLightAverageInHours,
        _sixMonthsRainSumInMm
    ) { temperature, windSpeed, dayLight, rainSum ->
        listOf(temperature, windSpeed, dayLight, rainSum)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedChartData = MutableStateFlow(Constants.chartSelection.map {
        SelectableItemWithIcon(
            label = it.key, isSelected = it.key == "Temperature", icon = it.value
        )
    })

    val selectedChartData = _selectedChartData.asStateFlow()


    private val _weatherInfo = MutableStateFlow<WeatherInfo?>(null)
    val weatherInfo = _weatherInfo.asStateFlow()

    fun getTidbitsByCountry(countryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = tidbitsRepository.getTidbitsByCountryName(countryName = countryName)
            when (result) {
                is Response.Success -> {
                    result.data.let { (tidbitData, youtubeVideoData) ->

                        _tidbitState.update {
                            it.copy(
                                tidbitData = tidbitData
                            )
                        }

                        _youtubeVideoState.update {
                            it.copy(
                                youtubeVideoData = youtubeVideoData
                            )
                        }
                    }
                }

                is Response.Error -> {
                    _tidbitState.update {
                        it.copy(
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    private fun getCurrentWeather(locationData: LocationData) {
        viewModelScope.launch {
            when (val result = weatherRepository.getWeatherData(locationData = locationData)) {

                is Response.Success -> {
                    _weatherInfo.update {
                        result.data
                    }
                }

                is Response.Error -> {
                    _eventsChannel.send(Event.ShowSnackbarMessage(result.message))
                }
            }
        }
    }

    fun getCelebritiesByCountry(countryName: String) {
        viewModelScope.launch {
            val result = celebrityRepository.getCelebritiesByCountryName(countryName = countryName)
            when (result) {
                is Response.Success -> {
                    _celebrityState.update {
                        it.copy(
                            celebrityData = result.data
                        )
                    }
                }

                is Response.Error -> {
                    _celebrityState.update {
                        it.copy(
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    fun setCurrentTidbitId(id: Int) {
        _tidbitState.update {
            it.copy(
                currentId = id
            )
        }
    }

    fun updateChartDataSelectedItem(label: String) {
        _selectedChartData.update {
            it.map { chartDataItem ->
                if (label != chartDataItem.label) {
                    chartDataItem.copy(
                        isSelected = false
                    )
                } else {
                    chartDataItem.copy(
                        isSelected = true
                    )
                }
            }
        }
    }


    fun setFavouriteStatus(isFavourite: Boolean) {
        _isCountryFavourite.update { isFavourite }
    }


    fun toggleCountryFavourite(country: CountryData) {
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
        _tidbitState.update {
            it.copy(
                cardState = newState
            )
        }
    }

    fun updateCelebrityCardState(newState: CardState) {
        _celebrityState.update {
            it.copy(
                cardState = newState
            )
        }
    }

    fun updateYoutubeCardState(newState: CardState) {
        _youtubeVideoState.update {
            it.copy(
                cardState = newState
            )
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
                    _countryGeolocation.value?.let { locationOverview ->
                        getTemperatureRangePastSixMonths(locationData = locationOverview)
                        getWindSpeedRangePastSixMonths(locationData = locationOverview)
                        getDayLightRangePastSixMonths(locationData = locationOverview)
                        getRainSumRangePastSixMonths(locationData = locationOverview)
                        getCurrentWeather(locationData = locationOverview)
                    }
                }

                is Response.Error -> {
                }
            }
        }
    }

    private fun getTemperatureRangePastSixMonths(locationData: LocationData) {
        viewModelScope.launch {
            val response =
                weatherRepository.getTemperatureRangePastSixMonths(locationData = locationData)
            when (response) {
                is Response.Success -> {
                    _sixMonthsTemperatureAverage.update { response.data }
                }

                is Response.Error -> {}
            }
        }
    }

    private fun getDayLightRangePastSixMonths(locationData: LocationData) {
        viewModelScope.launch {
            val response =
                weatherRepository.getDayLightRangePastSixMonths(locationData = locationData)
            when (response) {
                is Response.Success -> {
                    _sixMonthsDayLightAverageInHours.update { response.data }
                }

                is Response.Error -> {}
            }
        }
    }

    private fun getRainSumRangePastSixMonths(locationData: LocationData) {
        viewModelScope.launch {
            val response =
                weatherRepository.getRainSumRangePastSixMonths(locationData = locationData)
            when (response) {
                is Response.Success -> {
                    _sixMonthsRainSumInMm.update { response.data }
                }

                is Response.Error -> {}
            }
        }
    }

    private fun getWindSpeedRangePastSixMonths(locationData: LocationData) {
        viewModelScope.launch {
            val response =
                weatherRepository.getWindSpeedRangePastSixMonths(locationData = locationData)
            when (response) {
                is Response.Success -> {
                    _sixMonthsWindSpeedAverage.update { response.data }
                }

                is Response.Error -> {}
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
        _weatherInfo.update { null }
        _countryGeolocation.update { null }
        _tidbitState.update { TidbitState() }
        _celebrityState.update { CelebrityState() }
        _youtubeVideoState.update { YoutubeVideoState() }
        _sixMonthsTemperatureAverage.update { SixMonthsWeatherData(monthAverages = emptyList()) }
        _sixMonthsRainSumInMm.update { SixMonthsWeatherData(monthAverages = emptyList()) }
        _sixMonthsDayLightAverageInHours.update { SixMonthsWeatherData(monthAverages = emptyList()) }
        _sixMonthsWindSpeedAverage.update { SixMonthsWeatherData(monthAverages = emptyList()) }
    }

}