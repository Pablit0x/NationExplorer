package com.pscode.app.presentation.screens.countries.flag_game.game

import com.pscode.app.data.repository.MongoRepositoryImpl
import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.domain.model.Result
import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.utils.Constants
import com.pscode.app.utils.Constants.APP_ID
import com.pscode.app.utils.Response
import com.russhwolf.settings.Settings
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
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
    private val countryRepository: CountryRepository, private val savedResults: Settings
) : ViewModel() {

    companion object {
        const val NUMBER_OF_ROUNDS = 10
        const val DEFAULT_TIME = "00:00:000"
        const val DELAY_MILLIS = 50L
    }

    init {
        getAllCountries()
        loginToRealm()
    }

    private val _isDataReady = MutableStateFlow(false)
    val isDataReady = _isDataReady.asStateFlow()

    private val _round = MutableStateFlow(1)
    val round = _round.asStateFlow()

    private val _points = MutableStateFlow(0)
    val points = _points.asStateFlow()

    private var allCountries = emptyList<CountryOverview>()

    private val _targetCountries = MutableStateFlow(emptyList<CountryOverview>())

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

    private val _stopWatchTime = MutableStateFlow(DEFAULT_TIME)
    val stopWatchTime: StateFlow<String> = _stopWatchTime.asStateFlow()

    private var stopwatchJob: Job? = null

    private val _personalBest = MutableStateFlow(Pair(0, DEFAULT_TIME))
    val personalBest = _personalBest.asStateFlow()

    private val _isNewPersonalBest = MutableStateFlow(false)
    val isNewPersonalBest = _isNewPersonalBest.asStateFlow()

    private val _username = MutableStateFlow("")

    private val _showUsernameInputDialog = MutableStateFlow(false)
    val showUsernameInputDialog = _showUsernameInputDialog.asStateFlow()


    fun startNewGame() {
        resetGameSettings()
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
        _personalBest.update { pbResult }
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

    private fun getUserName() {
        val savedUsername = savedResults.getString(key = Constants.USERNAME_KEY, defaultValue = "")
        _username.update { savedUsername }
    }

    fun setUserName(username: String) {
        _username.update { username }
        savedResults.putString(key = Constants.USERNAME_KEY, value = username)
        _showUsernameInputDialog.update { false }
        _isNewPersonalBest.update { updatePersonalBestIfNeeded() }
        _showScore.update { true }
    }


    private fun startStopwatch() {
        stopStopwatch()
        val startTime = Clock.System.now()
        stopwatchJob = viewModelScope.launch(Dispatchers.Default) {
            while (isActive) {
                val elapsed = Clock.System.now() - startTime
                val formattedTime = formatStopWatchTime(elapsed = elapsed)
                _stopWatchTime.update { formattedTime }
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
            _showUsernameInputDialog.update { true }
        } else {
            _isNewPersonalBest.update { updatePersonalBestIfNeeded() }
            _showScore.update { true }
        }
    }

    private fun updatePersonalBestIfNeeded(): Boolean {
        val pbScore = personalBest.value.first
        val pbTime = personalBest.value.second

        val newScore = points.value
        val newTime = stopWatchTime.value

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
        _personalBest.update { Pair(newScore, newTime) }
        sendPersonalBestToOnlineLeaderboard()
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
        viewModelScope.launch {
            val result = countryRepository.getAllCountries()

            when (result) {
                is Response.Success -> {
                    allCountries = result.data
                    _isDataReady.update { true }
                }

                is Response.Error -> {
                    // TODO
                    _isDataReady.update { false }
                }
            }
        }
    }

    private fun loginToRealm() {
        viewModelScope.launch(Dispatchers.IO) {
            App.create(APP_ID).login(credentials = Credentials.anonymous(reuseExisting = true))
        }
    }

    private fun sendPersonalBestToOnlineLeaderboard() {
        val result = Result().apply {
            score = points.value
            time = stopWatchTime.value
            timeMillis = parseTimeToMillis(timeString = stopWatchTime.value)
            username = _username.value
        }

        viewModelScope.launch(Dispatchers.IO) {
            MongoRepositoryImpl.insertResult(result)
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
        _roundData.update { RoundData(targetCountry, shuffledOptions) }
    }


    private fun resetGameSettings() {
        _showUsernameInputDialog.update { false }
        _stopWatchTime.update { DEFAULT_TIME }
        _isNewPersonalBest.update { false }
        _round.update { 1 }
        _points.update { 0 }
        _targetCountries.update { emptyList() }
        _roundData.update { null }
        _isSelectionCorrect.update { false }
        _selectedFlag.update { null }
        _quizButtonState.update { QuizButtonState.NEXT(onNextClick = ::nextStage) }
        _showScore.update { false }
        _showQuizButton.update { false }
    }
}