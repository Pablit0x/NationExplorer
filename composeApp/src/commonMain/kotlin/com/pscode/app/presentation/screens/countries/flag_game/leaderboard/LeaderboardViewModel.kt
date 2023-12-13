package com.pscode.app.presentation.screens.countries.flag_game.leaderboard

import com.pscode.app.data.repository.MongoRepositoryImpl
import com.pscode.app.domain.model.Result
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LeaderboardViewModel : ViewModel() {

    private val _results = MutableStateFlow<List<Result>>(emptyList())
    val results = _results.asStateFlow()

    init {
        getAllResults()
    }

    private fun getAllResults() {
        viewModelScope.launch {
            MongoRepositoryImpl.getResults().collect { allResults ->
                _results.update {
                    allResults
                }
            }
        }
    }
}