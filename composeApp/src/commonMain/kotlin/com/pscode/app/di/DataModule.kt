package com.pscode.app.di

import com.pscode.app.data.cache.CountryCache
import com.pscode.app.data.remote.CountryApiImpl
import com.pscode.app.data.remote.WeatherApiImpl
import com.pscode.app.data.repository.CountryRepositoryImpl
import com.pscode.app.data.repository.WeatherRepositoryImpl
import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.domain.remote.CountryApi
import com.pscode.app.domain.remote.WeatherApi
import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.domain.repository.WeatherRepository
import io.github.xxfast.kstore.KStore
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val httpClient = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
}


val dataModule = module {
    single<CountryApi> { CountryApiImpl(httpClient = httpClient) }
    single<WeatherApi> { WeatherApiImpl(httpClient = httpClient) }
    single<KStore<List<CountryOverview>>> { CountryCache().cache }
    single<CountryRepository> {
        CountryRepositoryImpl(
            countryApi = get(),
            countryOverviewCache = get()
        )
    }
    single<WeatherRepository> { WeatherRepositoryImpl(weatherApi = get()) }
}