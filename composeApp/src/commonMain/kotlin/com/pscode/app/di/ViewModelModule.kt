package com.pscode.app.di

import com.pscode.app.presentation.screens.countries.detail.DetailViewModel
import com.pscode.app.presentation.screens.countries.flag_game.FlagGameViewModel
import com.pscode.app.presentation.screens.countries.overview.OverviewViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single<DetailViewModel> { DetailViewModel(weatherRepository = get()) }
    single<OverviewViewModel> { OverviewViewModel(countryRepository = get()) }
    single<FlagGameViewModel> { FlagGameViewModel(countryRepository = get(), savedResults = get()) }
}