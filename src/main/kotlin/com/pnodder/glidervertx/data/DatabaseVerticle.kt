package com.pnodder.glidervertx.data

import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import com.pnodder.glidervertx.services.JourneyService
import com.pnodder.glidervertx.services.JourneyServiceImpl
import com.pnodder.glidervertx.services.LocationService
import com.pnodder.glidervertx.services.LocationServiceImpl
import io.vertx.core.AbstractVerticle
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import org.slf4j.LoggerFactory

class DatabaseVerticle : AbstractVerticle() {

    private val log = LoggerFactory.getLogger(DatabaseVerticle::class.java)
    lateinit var mongoDatabase: MongoDatabase
    private lateinit var locationService: LocationService
    private lateinit var journeyService: JourneyService

    override fun start() {
        mongoDatabase = prepareDatabase()
        // register event bus message listener
        vertx.eventBus()
            .consumer<JsonObject>(config().getString(CONFIG_TIMETABLEDB_QUEUE, "ttdb.queue"), ::onMessage)
        locationService = LocationServiceImpl(LocationDao(mongoDatabase))
        journeyService = JourneyServiceImpl(JourneyOriginDao(mongoDatabase))
    }

    private fun onMessage(message: Message<JsonObject>) {
        log.info("Message received: ${getMessageString(message)}")
        when (val action = message.headers().get("action")) {
            "getLocationFromFullLocation" -> locationFromFullLocation(message)
            "getDeparturesFromLocation" -> departuresFromLocation(message)
            "getAllLocations" -> allLocations(message)
            else -> {
                log.info("Unable to handle message with action $action")
            }
        }
    }

    private fun locationFromFullLocation(message: Message<JsonObject>) {
        val locationQuery = message.body()
        val location = locationService.findLocationFromFullLocation(locationQuery.getString("fullLocation"))
        if (location.isNotEmpty()) {
            message.reply(JsonObject().put("result", location))
        } else {
            message.fail(69, "Empty response for query: $locationQuery")
        }
    }

    private fun departuresFromLocation(message: Message<JsonObject>) {
        val journeyOriginQuery = message.body()
        val departures = journeyService.findJourneyOriginByLocation(journeyOriginQuery.getString("location"))
        message.reply(JsonObject().put("result", departures))
    }

    private fun allLocations(message: Message<JsonObject>) {
        message.reply(JsonObject().put("result", locationService.findAll()))
    }

    private fun getMessageString(message: Message<JsonObject>) =
        "\n address: ${message.address()} \n headers: ${message.headers()} \n body: ${message.body()}"

    private fun prepareDatabase(): MongoDatabase {
        val mongoClient = MongoClient("localhost", 27017)
        return mongoClient.getDatabase("timetabledb")
    }

    companion object DatabaseConstants {
        const val MONGO_PORT = "mongo.port"
        const val MONGO_HOST = "mongo.host"
        const val MONGO_DB_NAME = "mongo.dbname"
        const val MONGO_MAX_POOL_SIZE = "mongo.max.pool.size"
        const val CONFIG_TIMETABLEDB_QUEUE = "ttdb.queue"
    }
}