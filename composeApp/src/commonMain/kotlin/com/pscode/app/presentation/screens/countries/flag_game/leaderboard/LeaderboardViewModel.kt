package com.pscode.app.presentation.screens.countries.flag_game.leaderboard

import com.pscode.app.domain.model.Result
import com.pscode.app.domain.repository.MongoRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.realm.kotlin.mongodb.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LeaderboardViewModel(
    private val mongoRepository: MongoRepository,
    val currentUser : User?
) : ViewModel() {

    private val _results = MutableStateFlow<List<Result>>(emptyList())
    val results = _results.asStateFlow()


    init {
        getAllResults()
    }

    private fun getAllResults() {
        viewModelScope.launch {
            mongoRepository.getResults().collect { allResults ->
                _results.update {
                    allResults
                }
            }
        }
    }
}