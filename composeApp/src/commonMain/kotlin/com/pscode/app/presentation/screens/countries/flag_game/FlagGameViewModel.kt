package com.pscode.app.presentation.screens.countries.flag_game

import com.pscode.app.domain.repository.CountryRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FlagGameViewModel(private val countryRepository: CountryRepository) : ViewModel() {

    private val _gameStage = MutableStateFlow(1)
    val gameStage = _gameStage.asStateFlow()


    fun nextStage() {
        val newStage = (gameStage.value + 1).coerceAtMost(10)
        _gameStage.update { newStage }
    }
}