package com.pnodder.glidervertx.services

import com.pnodder.glidervertx.data.LocationDao
import io.vertx.core.json.JsonArray

class LocationServiceImpl(private val locationDao: LocationDao) : LocationService {

    override fun findLocationFromFullLocation(fullLocation: String): String {
        return locationDao.findByFullLocation(fullLocation).first()?.getString("location") ?: ""
    }

    override fun findAll(): JsonArray {
        return JsonArray(locationDao.findAll().toList())
    }
}