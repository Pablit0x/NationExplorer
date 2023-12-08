package com.pscode.app.presentation.screens.countries.flag_game

import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.utils.Response
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlagGameViewModel(private val countryRepository: CountryRepository) : ViewModel() {

    init {
        getAllCountries()
    }

    private val _round = MutableStateFlow(1)
    val round = _round.asStateFlow()

    private val _allCountries = MutableStateFlow(emptyList<CountryOverview>())

    private val gameCountries = MutableStateFlow(emptyList<CountryOverview>())

    private val _roundData = MutableStateFlow<RoundData?>(null)
    val roundData = _roundData.asStateFlow()

    fun nextStage() {
        val nextRound = (round.value + 1).coerceAtMost(10)
        _round.update { nextRound }
        _roundData.update {
            playRound(nextRound)
        }
    }


    private fun getAllCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = countryRepository.getAllCountries()

            when (result) {
                is Response.Success -> {
                    _allCountries.update {
                        result.data
                    }
                }
                is Response.Error -> {
                    // Ignore for now
                }
            }
        }
    }

    fun startNewGame() {
        gameCountries.update { _allCountries.value.shuffled().take(10)}
        playRound(round = 1)
    }


    private fun playRound(round: Int): RoundData {
        val targetCountry = gameCountries.value[round]
        val options = _allCountries.value.shuffled().filter { it != targetCountry }.take(3) + targetCountry
        val shuffledOptions = options.shuffled()
        return RoundData(targetCountry, shuffledOptions)
    }

    fun checkAnswer(selectedFlag: String, targetCountry: CountryOverview): Boolean {
        return selectedFlag == targetCountry.flagUrl
    }
}