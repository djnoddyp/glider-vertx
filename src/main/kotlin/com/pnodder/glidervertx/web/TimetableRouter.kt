package com.pnodder.glidervertx.web

import com.pnodder.glidervertx.services.JourneyService
import com.pnodder.glidervertx.services.LocationService
import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpHeaders
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler


class TimetableRouter(
    private val journeyService: JourneyService,
    private val locationService: LocationService) : AbstractVerticle() {

    override fun start() {
        val router = Router.router(vertx)
        router.route().handler(BodyHandler.create())
        router.get("/journeyorigin/:fullLocation").handler(this::handleGetJourneysForLocation)
        router.get("/next/:fromLocation").handler(this::handleJourneyFromTo)
        vertx.createHttpServer().requestHandler(router).listen(8086)
    }

    private fun handleGetJourneysForLocation(routingContext: RoutingContext) {
        val location = locationService.findLocationFromFullLocation(routingContext.pathParam("fullLocation"))
        val journeyOriginArray = journeyService.findOriginByLocation(location)
        routingContext.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .end(journeyOriginArray.encode())
    }

    private fun handleJourneyFromTo(routingContext: RoutingContext) {
        val fromLocation = locationService.findLocationFromFullLocation(routingContext.pathParam("fromLocation"))
        val journeyOriginArray = journeyService.findNJourneysFromNow(fromLocation, 3)
        routingContext.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .end(journeyOriginArray.encode())
    }
}