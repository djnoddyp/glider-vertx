package com.pnodder.glidervertx.services

import io.vertx.core.json.JsonArray

interface LocationService {

    fun findLocationFromFullLocation(fullLocation: String): String

    fun findAll(): JsonArray

}