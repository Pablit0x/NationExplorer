package com.pscode.app.di

import com.pscode.app.data.cache.CountryCache
import com.pscode.app.data.remote.CountryApiImpl
import com.pscode.app.data.repository.CountryRepositoryImpl
import com.pscode.app.domain.model.Country
import com.pscode.app.domain.remote.CountryApi
import com.pscode.app.domain.repository.CountryRepository
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
    single<CountryApi> { CountryApiImpl(httpClient) }
    single<KStore<List<Country>>> { CountryCache().cache }
    single<CountryRepository> { CountryRepositoryImpl(get(), get()) }
}