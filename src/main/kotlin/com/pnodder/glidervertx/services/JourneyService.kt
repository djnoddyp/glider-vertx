package com.pnodder.glidervertx.services

import com.pnodder.glidervertx.dao.JourneyOriginDao
import io.vertx.core.json.JsonArray
import org.bson.Document
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

class JourneyService(private val journeyOriginDao: JourneyOriginDao) {

    fun findOriginByLocation(location: String): JsonArray {
        val array = JsonArray()
        journeyOriginDao.findJourneyOriginByLocation(location).forEach { jo -> array.add(jo) }
        return array
    }

    fun findNJourneysFromNow(location: String, numOfJourneys: Int): JsonArray {
        val array = JsonArray()
        journeyOriginDao.findJourneyOriginByLocation(location)
            .filter(this::checkDepartureTime)
            .take(numOfJourneys)
            .forEach { j -> array.add(j) }
        return array
    }

    private fun checkDepartureTime(doc: Document): Boolean {
        val depTime = LocalTime.ofInstant((doc.get("departureTime") as Date).toInstant(), ZoneId.systemDefault())
        return depTime.isAfter(LocalTime.now())
    }
}