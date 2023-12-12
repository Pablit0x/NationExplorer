package com.pscode.app.domain.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Result : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke()
    var username: String = ""
    var userId: String = ""
    var timeMillis: Long = 0L
    var time: String = "00:00:000"
    var score: Int = 0
}
