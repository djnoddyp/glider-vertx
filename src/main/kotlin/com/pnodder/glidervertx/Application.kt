package com.pnodder.glidervertx

import com.google.inject.BindingAnnotation
import com.google.inject.Guice
import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import com.pnodder.glidervertx.dao.JourneyOriginDao
import com.pnodder.glidervertx.dao.LocationDao
import com.pnodder.glidervertx.services.JourneyService
import com.pnodder.glidervertx.services.LocationService
import com.pnodder.glidervertx.web.TimetableRouter
import io.vertx.core.Vertx

fun main() {
//    val injector = Guice.createInjector(TimetableModule())
//    injector.injectMembers(TimetableRouter::class.java)
//    val reader = injector.getInstance(TimetableReader::class.java)
//    reader.read()
    val db = getMongoDatabase()
    val locationDao = LocationDao(db.getCollection("location"))
    val journeyOriginDao = JourneyOriginDao(db.getCollection("journey_origin"))
    val locationService = LocationService(locationDao)
    val journeyService = JourneyService(journeyOriginDao)

    val vertx = Vertx.vertx()
    val router = TimetableRouter(journeyService, locationService)
    vertx.deployVerticle(router)
}

fun getMongoDatabase(): MongoDatabase {
    val mongoClient = MongoClient("localhost", 27017)
    return mongoClient.getDatabase("timetabledb")
}

@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class RouteCollection
@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class JourneyCollection
@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class JourneyDestCollection
@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class JourneyIntCollection
@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class JourneyOrigCollection
@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class LocationCollection
@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class NoArgInit