package com.pscode.app.di

import com.pscode.app.data.cache.CountryCache
import com.pscode.app.data.cache.GeoLocationCache
import com.pscode.app.data.remote.CountryApiImpl
import com.pscode.app.data.remote.GeoLocationApiImpl
import com.pscode.app.data.remote.WeatherApiImpl
import com.pscode.app.data.repository.CountryRepositoryImpl
import com.pscode.app.data.repository.GeoLocationRepositoryImpl
import com.pscode.app.data.repository.MongoRepositoryImpl
import com.pscode.app.data.repository.WeatherRepositoryImpl
import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.domain.remote.CountryApi
import com.pscode.app.domain.remote.GeoLocationApi
import com.pscode.app.domain.remote.WeatherApi
import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.domain.repository.GeoLocationRepository
import com.pscode.app.domain.repository.MongoRepository
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
    single<GeoLocationApi> { GeoLocationApiImpl(httpClient = get()) }
    single<KStore<List<CountryOverview>>> { CountryCache().cache }
    single<Settings> { Settings() }
    single<CountryRepository> {
        CountryRepositoryImpl(
            countryApi = get(), countryOverviewCache = get()
        )
    }
    single<GeoLocationRepository> {
        GeoLocationRepositoryImpl(
            geoLocationApi = get(), geoLocationCache = GeoLocationCache().cache
        )
    }
    single<WeatherRepository> { WeatherRepositoryImpl(weatherApi = get()) }
    single<MongoRepository> { MongoRepositoryImpl(user = get()) }
}