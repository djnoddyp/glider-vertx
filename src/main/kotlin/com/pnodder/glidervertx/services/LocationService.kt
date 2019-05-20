package com.pnodder.glidervertx.services

import com.pnodder.glidervertx.dao.LocationDao

class LocationService(private val locationDao: LocationDao) {

    fun findLocationFromFullLocation(fullLocation: String): String {
        val location = locationDao.findByFullLocation(fullLocation)
        return location.first()?.get("location") as String
    }

}