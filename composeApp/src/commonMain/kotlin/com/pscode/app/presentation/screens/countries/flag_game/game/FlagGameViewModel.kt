package com.pscode.app.presentation.screens.countries.flag_game.game

import com.pscode.app.domain.model.CountryData
import com.pscode.app.domain.model.Result
import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.domain.repository.MongoRepository
import com.pscode.app.utils.Constants
import com.pscode.app.utils.Constants.DEFAULT_TIME
import com.pscode.app.utils.Constants.DELAY_MILLIS
import com.pscode.app.utils.Constants.NUMBER_OF_ROUNDS
import com.pscode.app.utils.Response
import com.russhwolf.settings.Settings
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlin.time.Duration

class FlagGameViewModel(
    private val countryRepository: CountryRepository,
    private val savedResults: Settings,
    private val mongoRepository: MongoRepository
) : ViewModel() {

    init {
        getAllCountries()
    }

    private val _isGameDataReady = MutableStateFlow(false)
    val isGameDataReady = _isGameDataReady.asStateFlow()

    private val _round = MutableStateFlow(1)
    val round = _round.asStateFlow()

    private val _userScore = MutableStateFlow(0)
    val userScore = _userScore.asStateFlow()

    private var allCountries = emptyList<CountryData>()

    private val _targetCountries = MutableStateFlow(emptyList<CountryData>())

    private val _currentRoundData = MutableStateFlow<RoundData?>(null)
    val currentRoundData = _currentRoundData.asStateFlow()

    private val _isSelectionCorrect = MutableStateFlow(false)
    val isSelectionCorrect = _isSelectionCorrect.asStateFlow()

    private val _selectedFlag = MutableStateFlow<String?>(null)
    val selectedFlag = _selectedFlag.asStateFlow()

    private val _quizButtonState =
        MutableStateFlow<QuizButtonState>(QuizButtonState.NEXT(onNextClick = ::nextStage))
    val quizButtonState = _quizButtonState.asStateFlow()

    private val _isScoreVisible = MutableStateFlow(false)
    val isScoreVisible = _isScoreVisible.asStateFlow()

    private val _isQuizButtonVisible = MutableStateFlow(false)
    val isQuizButtonVisible = _isQuizButtonVisible.asStateFlow()

    private val _currentStopWatchTime = MutableStateFlow(DEFAULT_TIME)
    val currentStopWatchTime: StateFlow<String> = _currentStopWatchTime.asStateFlow()

    private var stopwatchJob: Job? = null

    private val _personalBestScore = MutableStateFlow(Pair(0, DEFAULT_TIME))
    val personalBestScore = _personalBestScore.asStateFlow()

    private val _hasNewPersonalBest = MutableStateFlow(false)
    val hasNewPersonalBest = _hasNewPersonalBest.asStateFlow()

    private val _username = MutableStateFlow("")

    private val _isUsernameInputDialogVisible = MutableStateFlow(false)
    val isUsernameInputDialogVisible = _isUsernameInputDialogVisible.asStateFlow()


    fun startNewGame() {
        setCurrentGameCountries()
        startRound()
        startStopwatch()
        getPersonalBest()
        getUserName()
    }

    private fun getPersonalBest() {
        val pbScore = savedResults.getInt(key = Constants.SCORE_KEY, defaultValue = 0)
        val pbTime = savedResults.getString(key = Constants.TIME_KEY, defaultValue = DEFAULT_TIME)
        val pbResult = Pair(pbScore, pbTime)
        _personalBestScore.update { pbResult }
    }


    private fun checkAnswer() {
        val isAnswerCorrect = selectedFlag.value == currentRoundData.value?.targetCountry?.flagUrl
        _isSelectionCorrect.update { isAnswerCorrect }
        if (isAnswerCorrect) increasePoint()
    }

    fun selectFlag(flagUrl: String) {
        _selectedFlag.update { flagUrl }
        _isQuizButtonVisible.update { true }
        checkAnswer()
    }

    private fun getUserName() {
        val savedUsername = savedResults.getString(key = Constants.USERNAME_KEY, defaultValue = "")
        _username.update { savedUsername }
    }

    fun setUserName(username: String) {
        savedResults.putString(key = Constants.USERNAME_KEY, value = username)
        _username.update { username }
        _isUsernameInputDialogVisible.update { false }
        _hasNewPersonalBest.update { updatePersonalBestIfNeeded() }
        _isScoreVisible.update { true }
    }


    private fun startStopwatch() {
        stopStopwatch()
        val startTime = Clock.System.now()
        stopwatchJob = viewModelScope.launch(Dispatchers.Default) {
            while (isActive) {
                val elapsed = Clock.System.now() - startTime
                val formattedTime = formatStopWatchTime(elapsed = elapsed)
                _currentStopWatchTime.update { formattedTime }
                delay(DELAY_MILLIS)
            }
        }
    }

    private fun parseTimeToMillis(timeString: String): Long {
        val (minutes, seconds, millis) = timeString.split(":").map { it.toLongOrNull() ?: 0L }
        return (minutes * 60 * 1000) + (seconds * 1000) + millis
    }

    private fun compareTimes(currentTime: String, personalBest: String): Boolean {
        val currentTimeMillis = parseTimeToMillis(currentTime)
        val personalBestMillis = parseTimeToMillis(personalBest)
        val comparisonResult = currentTimeMillis.compareTo(personalBestMillis)
        return comparisonResult <= 0
    }


    private fun formatStopWatchTime(elapsed: Duration): String {
        val minutes = (elapsed.inWholeMinutes).toString().padStart(2, '0')
        val seconds = ((elapsed.inWholeSeconds) % 60).toString().padStart(2, '0')
        val millis = (elapsed.inWholeMilliseconds % 1000).toString().padStart(3, '0')

        return "$minutes:$seconds:$millis"
    }

    private fun stopStopwatch() {
        stopwatchJob?.cancel()
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
            QuizButtonState.FINISH(onFinishClick = { onFinishQuiz() })
        }
    }

    private fun onFinishQuiz() {
        stopStopwatch()
        if (_username.value.isBlank()) {
            _isUsernameInputDialogVisible.update { true }
        } else {
            _hasNewPersonalBest.update { updatePersonalBestIfNeeded() }
            _isScoreVisible.update { true }
        }
    }

    private fun updatePersonalBestIfNeeded(): Boolean {
        val pbScore = personalBestScore.value.first
        val pbTime = personalBestScore.value.second

        val newScore = userScore.value
        val newTime = currentStopWatchTime.value

        if (newScore > pbScore) {
            updatePersonalBest(newScore, newTime)
            return true
        } else if (newScore == pbScore && compareTimes(
                currentTime = newTime, personalBest = pbTime
            )
        ) {
            updatePersonalBest(newScore, newTime)
            return true
        }

        return false
    }

    private fun updatePersonalBest(newScore: Int, newTime: String) {
        savedResults.putInt(Constants.SCORE_KEY, newScore)
        savedResults.putString(Constants.TIME_KEY, newTime)
        _personalBestScore.update { Pair(newScore, newTime) }
        sendPersonalBestToOnlineLeaderboard()
    }


    private fun increasePoint() {
        _userScore.update { it + 1 }
    }

    private fun resetRound() {
        _isSelectionCorrect.update { false }
        _selectedFlag.update { null }
        _isQuizButtonVisible.update { false }
    }

    private fun getAllCountries() {
        viewModelScope.launch {
            when (val result = countryRepository.getAllCountries()) {
                is Response.Success -> {
                    allCountries = result.data.filterNot { it.name == "Antarctica" }
                    _isGameDataReady.update { true }
                }

                is Response.Error -> {
                    _isGameDataReady.update { false }
                }
            }
        }
    }

    private fun sendPersonalBestToOnlineLeaderboard() {
        val resultData = Result().apply {
            score = userScore.value
            time = currentStopWatchTime.value
            timeMillis = parseTimeToMillis(timeString = currentStopWatchTime.value)
            username = _username.value
        }
        viewModelScope.launch(Dispatchers.IO) {
            mongoRepository.upsertResult(resultData)
        }
    }

    private fun setCurrentGameCountries() {
        _targetCountries.update { allCountries.shuffled().take(10) }
    }


    private fun startRound() {
        val roundIndex = round.value - 1
        val targetCountry = _targetCountries.value[roundIndex]
        val options = allCountries.shuffled().filter { it != targetCountry }.take(3) + targetCountry
        val shuffledOptions = options.shuffled()
        _currentRoundData.update { RoundData(targetCountry, shuffledOptions) }
    }


    fun clear() {
        _isUsernameInputDialogVisible.update { false }
        _currentStopWatchTime.update { DEFAULT_TIME }
        _hasNewPersonalBest.update { false }
        _round.update { 1 }
        _userScore.update { 0 }
        _targetCountries.update { emptyList() }
        _currentRoundData.update { null }
        _isSelectionCorrect.update { false }
        _selectedFlag.update { null }
        _quizButtonState.update { QuizButtonState.NEXT(onNextClick = ::nextStage) }
        _isScoreVisible.update { false }
        _isQuizButtonVisible.update { false }
    }
}