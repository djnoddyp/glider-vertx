package com.pnodder.glidervertx

import com.google.inject.BindingAnnotation
import com.google.inject.Guice

fun main() {
    val injector = Guice.createInjector(TimetableModule())
    val reader = injector.getInstance(TimetableReader::class.java)
    reader.read()
}

@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class RouteCollection
@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class JourneyCollection
@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class JourneyDestCollection
@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class JourneyIntCollection
@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class JourneyOrigCollection
@BindingAnnotation @Retention(AnnotationRetention.RUNTIME) annotation class LocationCollection