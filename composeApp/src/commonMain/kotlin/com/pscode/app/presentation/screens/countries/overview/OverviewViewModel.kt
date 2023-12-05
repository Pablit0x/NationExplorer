package com.pscode.app.presentation.screens.countries.overview

import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.utils.Response
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OverviewViewModel(private val countryRepository: CountryRepository) : ViewModel() {

    private val _state = MutableStateFlow(OverviewUiState())
    val state = _state.asStateFlow()

    private val errorChannel = Channel<ErrorEvent>()
    val errorEventsChannelFlow = errorChannel.receiveAsFlow()

    init {
        getAllCountries()
    }


    private fun getAllCountries() {
        viewModelScope.launch {
            when (val result = countryRepository.getAllCountries()) {
                is Response.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false, countries = result.data
                        )
                    }
                }
                is Response.Error -> {
                    errorChannel.send(ErrorEvent.ShowSnackbarMessage(message = result.message))

                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

}