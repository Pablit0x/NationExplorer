package com.pscode.app.di

import com.pscode.app.data.cache.CountryCache
import com.pscode.app.data.cache.GeolocationCache
import com.pscode.app.data.remote.CelebrityApiImpl
import com.pscode.app.data.remote.CountryApiImpl
import com.pscode.app.data.remote.GeolocationApiImpl
import com.pscode.app.data.remote.TidbitsApiImpl
import com.pscode.app.data.remote.WeatherApiImpl
import com.pscode.app.data.repository.CelebrityRepositoryImpl
import com.pscode.app.data.repository.CountryRepositoryImpl
import com.pscode.app.data.repository.GeolocationRepositoryImpl
import com.pscode.app.data.repository.MongoRepositoryImpl
import com.pscode.app.data.repository.TidbitsRepositoryImpl
import com.pscode.app.data.repository.WeatherRepositoryImpl
import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.domain.remote.CelebrityApi
import com.pscode.app.domain.remote.CountryApi
import com.pscode.app.domain.remote.GeolocationApi
import com.pscode.app.domain.remote.TidbitsApi
import com.pscode.app.domain.remote.WeatherApi
import com.pscode.app.domain.repository.CelebrityRepository
import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.domain.repository.GeolocationRepository
import com.pscode.app.domain.repository.MongoRepository
import com.pscode.app.domain.repository.TidbitsRepository
import com.pscode.app.domain.repository.WeatherRepository
import com.pscode.app.utils.Constants.APP_ID
import com.russhwolf.settings.Settings
import io.github.xxfast.kstore.KStore
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.User
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }
    single<User?> { App.create(APP_ID).currentUser }
    single<CountryApi> { CountryApiImpl(httpClient = get()) }
    single<WeatherApi> { WeatherApiImpl(httpClient = get()) }
    single<GeolocationApi> { GeolocationApiImpl(httpClient = get()) }
    single<KStore<List<CountryOverview>>> { CountryCache().cache }
    single<Settings> { Settings() }
    single<CountryRepository> {
        CountryRepositoryImpl(
            countryApi = get(), countryOverviewCache = get()
        )
    }
    single<GeolocationRepository> {
        GeolocationRepositoryImpl(
            geolocationApi = get(), geolocationCache = GeolocationCache().cache
        )
    }
    single<WeatherRepository> { WeatherRepositoryImpl(weatherApi = get()) }
    single<MongoRepository> { MongoRepositoryImpl(user = get()) }
    single<TidbitsApi> { TidbitsApiImpl(httpClient = get()) }
    single<TidbitsRepository> { TidbitsRepositoryImpl(tidbitsApi = get()) }
    single<CelebrityApi> { CelebrityApiImpl(httpClient = get()) }
    single<CelebrityRepository> { CelebrityRepositoryImpl(celebrityApi = get()) }
}