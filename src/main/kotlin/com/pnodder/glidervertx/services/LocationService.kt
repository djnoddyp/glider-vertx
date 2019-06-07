//package com.pnodder.glidervertx.services
//
//import com.pnodder.glidervertx.dao.LocationDao
//import org.bson.Document
//
//class LocationService(private val locationDao: LocationDao) {
//
//    fun findLocationFromFullLocation(fullLocation: String): String {
//        val location = locationDao.findByFullLocation(fullLocation)
//        return location.first()?.get("location") as String
//    }
//
//    fun findAll(): List<Any?> {
//        return locationDao.findAllFullLocations().sortedBy { selector(it, "fullLocation") }
//    }
//
//    fun selector(doc: Document, field: String) = doc.get(field) as String
//
//}