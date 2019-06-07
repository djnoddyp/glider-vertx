//package com.pnodder.glidervertx.web
//
//import com.pnodder.glidervertx.services.JourneyService
//import com.pnodder.glidervertx.services.LocationService
//import io.vertx.core.AbstractVerticle
//import io.vertx.core.http.HttpHeaders
//import io.vertx.core.json.JsonArray
//import io.vertx.ext.web.Router
//import io.vertx.ext.web.RoutingContext
//import io.vertx.ext.web.handler.BodyHandler
//import org.slf4j.LoggerFactory
//
//
//class TimetableRouter(val journeyService: JourneyService, val locationService: LocationService) : AbstractVerticle() {
//    
//    private val log = LoggerFactory.getLogger(TimetableRouter::class.java)
//
//    override fun start() {
//        val router = Router.router(vertx)
//        router.route().handler(BodyHandler.create())
//        router.get("/journeyorigin/:fullLocation").handler(this::handleGetJourneysForLocation)
//        router.get("/next/:fromLocation").handler(this::handleJourneyFromTo)
//        router.get("/location/all").handler(this::handleFindAllLocations)
//        vertx.createHttpServer().requestHandler(router).listen(8086)
//    }
//
//    private fun handleGetJourneysForLocation(routingContext: RoutingContext) {
//        log.info("Handling journey origin request")
//        val location = locationService.findLocationFromFullLocation(routingContext.pathParam("fullLocation"))
//        var array = JsonArray(journeyService.findJourneyOriginByLocation(location))
//        if (array.isEmpty) {
//            array = JsonArray(journeyService.findJourneyIntermediateByLocation(location))
//        }
//        generateResponse(routingContext, array)
//    }
//
//    private fun handleJourneyFromTo(routingContext: RoutingContext) {
//        log.info("Handling upcoming journeys request")
//        val fromLocation = locationService.findLocationFromFullLocation(routingContext.pathParam("fromLocation"))
//        val array = JsonArray(journeyService.findNJourneysFromNow(fromLocation, 3))
//        generateResponse(routingContext, array)
//    }
//    
//    private fun handleFindAllLocations(routingContext: RoutingContext) {
//        log.info("Handling all locations request")
//        val array = JsonArray(locationService.findAll())
//        generateResponse(routingContext, array)
//    }
//
//    private fun generateResponse(routingContext: RoutingContext, array: JsonArray) {
//        routingContext.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json").end(array.encode())
//    }
//}