package com.pnodder.glidervertx

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient
import org.slf4j.LoggerFactory


class MainVerticle : AbstractVerticle() {

    private val log = LoggerFactory.getLogger(MainVerticle::class.java)
    lateinit var mongoClient: MongoClient
    val MONGO_PORT = 27017
    val MONGO_HOST = "127.0.0.1"
    val MONGO_DB_NAME = "timetabledb"

//    val locationDao = LocationDao(mongoClient))
//    val journeyOriginDao = JourneyOriginDao(db.getCollection("journey_origin"))
//    val journeyIntermediateDao = JourneyIntermediateDao(db.getCollection("journey_intermediate"))
//    val locationService = LocationService(locationDao)
//    val journeyService = JourneyService(journeyOriginDao)

    override fun start(startFuture: Future<Void>) {
        prepareDatabase()
        future.setHandler(startFuture)
    }

    fun prepareDatabase() {
        val config = JsonObject()
            .put("db_name", MONGO_DB_NAME)
            .put("host", MONGO_HOST)
            .put("port", MONGO_PORT)
            .put("maxPoolSize", 20)
        mongoClient = MongoClient.createShared(vertx, config)
    }

}