package com.pscode.app.data.repository

import com.pscode.app.domain.model.ResultData
import com.pscode.app.domain.repository.MongoRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.User
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

class MongoRepositoryImpl(
    private val user: User?,
) : MongoRepository {

    private lateinit var realm: Realm

    init {
        configureRealm()
    }

    override fun configureRealm() {
        if (user != null) {
            val config = SyncConfiguration.Builder(
                user, setOf(ResultData::class)
            ).initialSubscriptions { sub ->
                add(query = sub.query<ResultData>("_id != $0", ObjectId()))
            }.log(LogLevel.ALL).build()
            realm = Realm.open(config)
        }
    }

    override fun getResults(): Flow<List<ResultData>> {
        return realm.query<ResultData>().asFlow().map { listOfResults ->
            listOfResults.list.sortedWith(
                compareBy({ -it.score }, { it.timeMillis })
            )
        }
    }

    override suspend fun upsertResult(resultData: ResultData) {
        if (user != null) {
            realm.write {
                val queriedResultData =
                    query<ResultData>(query = "userId == $0", user.id).find().firstOrNull()
                if (queriedResultData == null) {
                    copyToRealm(resultData.apply {
                        userId = user.id
                    })
                } else {
                    run {
                        queriedResultData.score = resultData.score
                        queriedResultData.time = resultData.time
                        queriedResultData.timeMillis = resultData.timeMillis
                    }
                }
            }
        }
    }
}