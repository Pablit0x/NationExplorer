package com.pscode.app.di

import com.pscode.app.presentation.screens.countries.detail.DetailViewModel
import com.pscode.app.presentation.screens.countries.flag_game.game.FlagGameViewModel
import com.pscode.app.presentation.screens.countries.flag_game.leaderboard.LeaderboardViewModel
import com.pscode.app.presentation.screens.countries.overview.OverviewViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single<DetailViewModel> {
        DetailViewModel(
            weatherRepository = get(),
            geolocationRepository = get(),
            tidbitsRepository = get(),
            networkConnectivity = get()
        )
    }
    single<OverviewViewModel> { OverviewViewModel(countryRepository = get()) }
    single<FlagGameViewModel> {
        FlagGameViewModel(
            countryRepository = get(), savedResults = get(), mongoRepository = get()
        )
    }
    single<LeaderboardViewModel> {
        LeaderboardViewModel(
            mongoRepository = get(), currentUser = get()
        )
    }
}