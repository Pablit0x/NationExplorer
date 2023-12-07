package com.pscode.app.presentation.screens.countries.detail

import com.pscode.app.domain.model.WeatherOverview
import com.pscode.app.domain.repository.WeatherRepository
import com.pscode.app.presentation.screens.shared.ErrorEvent
import com.pscode.app.utils.Response
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _cityWeather = MutableStateFlow<WeatherOverview?>(null)
    val cityWeather = _cityWeather.asStateFlow()

    private val errorChannel = Channel<ErrorEvent>()
    val errorEventsChannelFlow = errorChannel.receiveAsFlow()

    fun getWeatherByCity(cityName: String) {
        _cityWeather.update { null }

        viewModelScope.launch(Dispatchers.IO) {
            val result = weatherRepository.getWeatherByCity(cityName = cityName)

            when (result) {
                is Response.Success -> {
                    _cityWeather.update {
                        result.data
                    }
                }

                is Response.Error -> {
                    errorChannel.send(ErrorEvent.ShowSnackbarMessage(message = result.message))
                }
            }
        }
    }
}