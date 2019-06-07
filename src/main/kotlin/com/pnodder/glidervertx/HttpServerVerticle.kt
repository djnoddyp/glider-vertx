package com.pnodder.glidervertx

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.http.HttpHeaders
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.FindOptions
import io.vertx.ext.mongo.MongoClient
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import org.slf4j.LoggerFactory
import java.time.LocalTime
import java.time.ZoneId

class HttpServerVerticle : AbstractVerticle() {

    private val log = LoggerFactory.getLogger(HttpServerVerticle::class.java)
    lateinit var mongoClient: MongoClient
    val SERVER_PORT = 8086

    override fun start(startFuture: Future<Void>) {
        val server = vertx.createHttpServer()
        val router = Router.router(vertx)
        router.route().handler(BodyHandler.create())
        router.get("/journeyorigin/:fullLocation").handler(this::handleGetJourneysForLocation)
        router.get("/next/:fromLocation").handler(this::handleJourneyFromTo)
        router.get("/location/all").handler(this::handleFindAllLocations)
        server
            .requestHandler(router)
            .listen(SERVER_PORT) { ar ->
                if (ar.succeeded()) {
                    log.info("HTTP server up and running on port $SERVER_PORT")
                    startFuture.complete()
                } else {
                    log.info("HTTP server encountered a problem starting: ${ar.cause()}")
                    startFuture.fail(ar.cause())
                }
            }
    }

    private fun handleGetJourneysForLocation(routingContext: RoutingContext) {
        log.info("Handling journey origin request")
        val locationQuery = JsonObject().put("fullLocation", routingContext.pathParam("fullLocation"))

        mongoClient.findOne("location", locationQuery, null) { res1 ->

            // How can it succeed but result is null??? seems like a bug
            if (res1.succeeded() && res1.result() != null) {
                val journeyOriginQuery = JsonObject().put("location", res1.result().getValue("location"))

                mongoClient.find("journey_origin", journeyOriginQuery) { res2 ->
                    if (res2.succeeded()) {
                        if (res2.result().isEmpty()) {
                            // get intermediate journeys
                            log.info("Empty location array")
                            routingContext.fail(res2.cause())
                        } else {
                            generateResponse(routingContext, JsonArray(res2.result()))
                        }
                    } else {
                        log.info("journey_origin query failed: ${journeyOriginQuery.encode()}")
                        routingContext.fail(res2.cause());
                    }
                }

            } else {
                log.info("location query failed: ${locationQuery.encode()}")
                routingContext.fail(res1.cause())
            }
        }

//        val location = locationService.findLocationFromFullLocation(routingContext.pathParam("fullLocation"))
//        var array = JsonArray(journeyService.findJourneyOriginByLocation(location))
//        if (array.isEmpty) {
//            array = JsonArray(journeyService.findJourneyIntermediateByLocation(location))
//        }
    }

    private fun handleJourneyFromTo(routingContext: RoutingContext) {
        log.info("Handling upcoming journeys request")
        val locationQuery = JsonObject().put("fullLocation", routingContext.pathParam("fromLocation"))

        mongoClient.findOne("location", locationQuery, null) { res1 ->

            // How can it succeed but result is null??? seems like a bug
            if (res1.succeeded() && res1.result() != null) {
                val journeyOriginQuery = JsonObject().put("location", res1.result().getValue("location"))

                mongoClient.find("journey_origin", journeyOriginQuery) { res2 ->
                    if (res2.succeeded()) {
                        if (res2.result().isEmpty()) {
                            // get intermediate journeys
                            log.info("Empty location array")
                            routingContext.fail(res2.cause())
                        } else {
                            val list = res2.result()
                                .filter(this::isDepartureTimeAfterNow)
                                .take(3)
                            generateResponse(routingContext, JsonArray(list))
                        }
                    } else {
                        log.info("journey_origin query failed: ${journeyOriginQuery.encode()}")
                        routingContext.fail(res2.cause());
                    }
                }
            } else {
                log.info("location query failed: ${locationQuery.encode()}")
                routingContext.fail(res1.cause())
            }
        }


//        val fromLocation = locationService.findLocationFromFullLocation(routingContext.pathParam("fromLocation"))
//        val array = JsonArray(journeyService.findNJourneysFromNow(fromLocation, 3))
//        generateResponse(routingContext, array)
    }

    private fun handleFindAllLocations(routingContext: RoutingContext) {
        log.info("Handling all locations request")
        val options = FindOptions()
            .setFields(JsonObject().put("fullLocation", true)) // select fullLocation
            .setSort(JsonObject().put("fullLocation", 1)) // sort asc

        mongoClient.findWithOptions("location", JsonObject(), options) { res ->
            if (res.succeeded()) {
                generateResponse(routingContext, JsonArray(res.result()))
            } else {
                routingContext.fail(res.cause())
            }
        }
//        val array = JsonArray(locationService.findAll())
//        generateResponse(routingContext, array)
    }

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
}