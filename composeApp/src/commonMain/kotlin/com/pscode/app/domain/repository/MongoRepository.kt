package com.pscode.app.domain.repository

import kotlinx.coroutines.flow.Flow
import com.pscode.app.domain.model.Result

interface MongoRepository {
    fun configureRealm()
    fun getResults(): Flow<List<Result>>
    suspend fun upsertResult(resultData: Result)
}