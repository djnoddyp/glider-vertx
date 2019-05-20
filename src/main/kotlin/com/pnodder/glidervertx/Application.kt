package com.pnodder.glidervertx

import com.google.inject.BindingAnnotation
import com.google.inject.Guice
import com.pnodder.glidervertx.web.TimetableRouter
import io.vertx.core.Vertx

fun main() {
//    val injector = Guice.createInjector(TimetableModule())
//    injector.injectMembers(TimetableRouter::class.java)
//    //val reader = injector.getInstance(TimetableReader::class.java)
//    //reader.read()
    
    val vertx = Vertx.vertx()
    vertx.deployVerticle(TimetableRouter::class.qualifiedName)
}

@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class RouteCollection
@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class JourneyCollection
@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class JourneyDestCollection
@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class JourneyIntCollection
@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class JourneyOrigCollection
@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class LocationCollection
@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class NoArgInit