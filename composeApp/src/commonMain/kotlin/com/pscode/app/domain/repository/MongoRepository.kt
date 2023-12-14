package com.pscode.app.domain.repository

import com.pscode.app.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface MongoRepository {
    fun configureRealm()
    fun getResults(): Flow<List<Result>>
    suspend fun upsertResult(result: Result)
}