package com.pnodder.glidervertx.services

import com.google.inject.Inject
import com.pnodder.glidervertx.dao.LocationDao

class LocationService @Inject constructor(val locationDao: LocationDao) {

    fun findLocationFromFullLocation(fullLocation: String): String {
        val location = locationDao.findByFullLocation(fullLocation)
        return location.first().get("location") as String
    }

}