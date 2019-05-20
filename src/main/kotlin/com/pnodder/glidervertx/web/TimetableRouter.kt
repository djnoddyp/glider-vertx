package com.pnodder.glidervertx.web

import com.mongodb.MongoClient
import com.pnodder.glidervertx.dao.JourneyOriginDao
import com.pnodder.glidervertx.dao.LocationDao
import com.pnodder.glidervertx.services.LocationService
import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpHeaders
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler


class TimetableRouter : AbstractVerticle() {

    private val mongoClient = MongoClient("localhost", 27017)
    private val db = mongoClient.getDatabase("timetabledb")
    private val locationDao = LocationDao(db.getCollection("location"))
    private val journeyOriginDao = JourneyOriginDao(db.getCollection("journey_origin"))
    private val locationService = LocationService(locationDao)
    
    override fun start() {
        val router = Router.router(vertx)
        router.route().handler(BodyHandler.create())
        router.get("/journeyorigin/:fullLocation").handler(this::handleGetJourneyForLocation)
        vertx.createHttpServer().requestHandler(router).listen(8086)
    }
    
    fun handleGetJourneyForLocation(routingContext: RoutingContext) {
        val location = locationService.findLocationFromFullLocation(routingContext.pathParam("fullLocation"))
        val journeyOrigin = journeyOriginDao.findJourneyOriginByLocation(location)
        val collection = journeyOrigin.map { j -> JsonObject(j) }.toList()
        routingContext.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json").end(collection.toString())
    }
}