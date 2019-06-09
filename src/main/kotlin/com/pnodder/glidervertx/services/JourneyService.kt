package com.pnodder.glidervertx.services

import io.vertx.core.json.JsonArray

interface JourneyService {

    fun findJourneyOriginByLocation(location: String): JsonArray

    fun findJourneyIntermediateByLocation(location: String): JsonArray

}