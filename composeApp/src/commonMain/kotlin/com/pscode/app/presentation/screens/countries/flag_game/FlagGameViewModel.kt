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

    companion object {
        const val NUMBER_OF_ROUNDS = 10
    }

    init {
        getAllCountries()
    }

    private val _isDataReady = MutableStateFlow(false)
    val isDataReady = _isDataReady.asStateFlow()

    private val _round = MutableStateFlow(1)
    val round = _round.asStateFlow()

    private val _points = MutableStateFlow(0)
    val points = _points.asStateFlow()

    private val _allCountries = MutableStateFlow(emptyList<CountryOverview>())

    private val _gameCountries = MutableStateFlow(emptyList<CountryOverview>())

    private val _roundData = MutableStateFlow<RoundData?>(null)
    val roundData = _roundData.asStateFlow()

    private val _isSelectionCorrect = MutableStateFlow(false)
    val isSelectionCorrect = _isSelectionCorrect.asStateFlow()

    private val _selectedFlag = MutableStateFlow<String?>(null)
    val selectedFlag = _selectedFlag.asStateFlow()

    private val _quizButtonState =
        MutableStateFlow<QuizButtonState>(QuizButtonState.NEXT(onNextClick = ::nextStage))
    val quizButtonState = _quizButtonState.asStateFlow()

    private val _showScore = MutableStateFlow(false)
    val showScore = _showScore.asStateFlow()

    private val _showQuizButton = MutableStateFlow(false)
    val showQuizButton = _showQuizButton.asStateFlow()


    fun startNewGame() {
        resetGameSettings()
        setCurrentGameCountries()
        startRound()
    }

    fun checkAnswer() {
        val isAnswerCorrect = selectedFlag.value == roundData.value?.targetCountry?.flagUrl
        _isSelectionCorrect.update { isAnswerCorrect }
        if (isAnswerCorrect) increasePoint()
    }

    fun setSelectedFlag(flagUrl: String) {
        _selectedFlag.update { flagUrl }
        _showQuizButton.update { true }
    }


    private fun nextStage() {
        if (round.value < NUMBER_OF_ROUNDS) {
            _round.update { it + 1 }
            resetRound()
            startRound()
            updateIsLastRound(round.value)
        }
    }

    private fun updateIsLastRound(currentRound: Int) {
        if (currentRound == NUMBER_OF_ROUNDS) _quizButtonState.update {
            QuizButtonState.FINISH(onFinishClick = {
                _showScore.update { true }
            })
        }
    }

    private fun increasePoint() {
        _points.update { it + 1 }
    }

    private fun resetRound() {
        _isSelectionCorrect.update { false }
        _selectedFlag.update { null }
        _showQuizButton.update { false }
    }

    private fun getAllCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = countryRepository.getAllCountries()

            when (result) {
                is Response.Success -> {
                    _allCountries.update {
                        result.data
                    }
                    _isDataReady.update { true }
                }

                is Response.Error -> {
                    // TODO
                    _isDataReady.update { false }
                }
            }
        }
    }

    private fun setCurrentGameCountries() {
        _gameCountries.update { _allCountries.value.shuffled().take(10) }
    }


    private fun startRound() {
        val roundIndex = round.value - 1
        val targetCountry = _gameCountries.value[roundIndex]
        val options =
            _allCountries.value.shuffled().filter { it != targetCountry }.take(3) + targetCountry
        val shuffledOptions = options.shuffled()
        _roundData.update { RoundData(targetCountry, shuffledOptions) }
    }


    private fun resetGameSettings() {
        _round.update { 1 }
        _points.update { 0 }
        _gameCountries.update { emptyList() }
        _roundData.update { null }
        _isSelectionCorrect.update { false }
        _selectedFlag.update { null }
        _quizButtonState.update { QuizButtonState.NEXT(onNextClick = ::nextStage) }
        _showScore.update { false }
        _showQuizButton.update { false }
    }
}