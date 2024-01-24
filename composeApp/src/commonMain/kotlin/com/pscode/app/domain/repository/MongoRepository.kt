package com.pscode.app.domain.repository

import com.pscode.app.domain.model.ResultData
import kotlinx.coroutines.flow.Flow

interface MongoRepository {
    fun configureRealm()
    fun getResults(): Flow<List<ResultData>>
    suspend fun upsertResult(resultData: ResultData)
}