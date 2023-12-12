package com.pscode.app.data.repository

import com.pscode.app.domain.model.Result
import com.pscode.app.domain.repository.MongoRepository
import com.pscode.app.utils.Constants.APP_ID
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

object MongoRepositoryImpl : MongoRepository {

    private val app = App.create(APP_ID)
    private val user = app.currentUser
    private lateinit var realm: Realm

    init {
        configureRealm()
    }

    override fun configureRealm() {
        if (user != null) {
            val config = SyncConfiguration.Builder(
                user, setOf(Result::class)
            ).initialSubscriptions { sub ->
                add(query = sub.query<Result>("_id != $0", ObjectId()))
            }.log(LogLevel.ALL).build()
            realm = Realm.open(config)
        }
    }

    override fun getResults(): Flow<List<Result>> {
        return realm.query<Result>().asFlow().map { it.list }
    }

    override suspend fun insertResult(result: Result) {
        if (user != null) {
            realm.write {
                try {
                    copyToRealm(result.apply {
                        userId = user.id
                    })
                } catch (ignored: Exception) {
                }
            }
        }
    }

}