package com.pscode.app.di

import com.pscode.app.presentation.screens.countries.detail.DetailViewModel
import com.pscode.app.presentation.screens.countries.overview.OverviewViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single<DetailViewModel> { DetailViewModel(get()) }
    single<OverviewViewModel> { OverviewViewModel(get()) }
}