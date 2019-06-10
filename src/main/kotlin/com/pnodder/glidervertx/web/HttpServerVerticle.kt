package com.pnodder.glidervertx.web

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.http.HttpHeaders
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import org.slf4j.LoggerFactory
import java.time.LocalTime
import java.time.ZoneId

class HttpServerVerticle : AbstractVerticle() {

    private val log = LoggerFactory.getLogger(HttpServerVerticle::class.java)
    private var ttdbQueue = "ttdb.queue"

    override fun start(startFuture: Future<Void>) {
        ttdbQueue = config().getString(CONFIG_TIMETABLEDB_QUEUE, "ttdb.queue")
        val server = vertx.createHttpServer()

        val router = Router.router(vertx)
        router.route().handler(BodyHandler.create())
        router.get("/journeyorigin/:fullLocation").handler(::handleGetJourneysForLocation)
        router.get("/next/:fromLocation").handler(::handleNextJourneysFrom)
        router.get("/location/all").handler(::handleFindAllLocations)

        val portNumber = config().getInteger(CONFIG_HTTP_SERVER_PORT, 8086)
        server
            .requestHandler(router)
            .listen(portNumber) { ar ->
                if (ar.succeeded()) {
                    log.info("HTTP server up and running on port $portNumber")
                    startFuture.complete()
                } else {
                    log.info("HTTP server encountered a problem starting: ${ar.cause()}")
                    startFuture.fail(ar.cause())
                }
            }
    }

    private fun handleGetJourneysForLocation(routingContext: RoutingContext) {
        log.info("Handling journey origin request")

        val composeFuture = getLocationFromFullLocation(routingContext.pathParam("fullLocation"))
        composeFuture
            .compose(::getDeparturesFromLocation)
            .setHandler { ar ->
                if (ar.succeeded()) {
                    generateResponse(routingContext, JsonArray(ar.result().toList()))
                } else {
                    //get intermediate journeys
                    log.info("Empty location array")
                    routingContext.fail(ar.cause())
                }
            }
    }

    private fun handleNextJourneysFrom(routingContext: RoutingContext) {
        log.info("Handling upcoming journeys request")

        val composeFuture = getLocationFromFullLocation(routingContext.pathParam("fromLocation"))
        composeFuture
            .compose(::getDeparturesFromLocation)
            .setHandler { ar ->
                if (ar.succeeded()) {
                    val list = ar.result()
                        .map(::toJsonObject)
                        .filter(::isDepartureTimeAfterNow)
                        .take(3)
                    generateResponse(routingContext, JsonArray(list))
                } else {
                    //get intermediate journeys
                    log.info("Empty location array")
                    routingContext.fail(ar.cause())
                }
            }
    }

    private fun handleFindAllLocations(routingContext: RoutingContext) {
        log.info("Handling all locations request")
        val options = DeliveryOptions().addHeader("action", "getAllLocations")

        vertx.eventBus().send<JsonObject>(ttdbQueue, JsonObject(), options) { reply ->
            if (reply.succeeded()) {
                val locations = reply.result().body().getJsonArray("result")
                generateResponse(routingContext, locations)
            } else {

            }
        }
    }


    private fun getLocationFromFullLocation(fullLocation: String): Future<String> {
        val future = Future.future<String>()
        val locationQuery = JsonObject().put("fullLocation", fullLocation)
        val options = DeliveryOptions().addHeader("action", "getLocationFromFullLocation")

        vertx.eventBus().send<JsonObject>(ttdbQueue, locationQuery, options) { reply ->
            if (reply.succeeded()) {
                val response = reply.result().body().getString("result")
                log.info("location data received: $response")
                future.complete(response)
            } else {
                log.info("location request fucked up: ${locationQuery.encode()}")
                future.fail(reply.cause())
            }
        }
        return future
    }

    private fun getDeparturesFromLocation(location: String): Future<JsonArray> {
        val future = Future.future<JsonArray>()
        val journeyOriginQuery = JsonObject().put("location", location)
        val options = DeliveryOptions().addHeader("action", "getDeparturesFromLocation")

        vertx.eventBus().send<JsonObject>(ttdbQueue, journeyOriginQuery, options) { reply ->
            if (reply.succeeded()) {
                val response = reply.result().body().getJsonArray("result")
                log.info("journey origin data received: $response")
                future.complete(response)
            } else {
                log.info("journey origin request fucked up: ${journeyOriginQuery.encode()}")
                future.fail(reply.cause())
            }
        }
        return future
    }

    private fun toJsonObject(any: Any) = JsonObject(any.toString())

    private fun generateResponse(routingContext: RoutingContext, array: JsonArray) {
        routingContext.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json").end(array.encode())
    }

    private fun isDepartureTimeAfterNow(jsonObject: JsonObject): Boolean {
        val time = jsonObject.getJsonObject("departureTime")
            .getInstant("\$date")
            .atZone(ZoneId.systemDefault())
            .toLocalTime()
        return time.isAfter(LocalTime.now())
    }

    companion object HttpServerConstants {
        const val SERVER_PORT = 8086
        const val CONFIG_TIMETABLEDB_QUEUE = "ttdb.queue"
        const val CONFIG_HTTP_SERVER_PORT = "http.server.port"
    }
}