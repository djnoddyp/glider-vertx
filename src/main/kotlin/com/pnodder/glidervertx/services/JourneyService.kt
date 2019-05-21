package com.pnodder.glidervertx.services

import com.pnodder.glidervertx.dao.JourneyOriginDao
import org.bson.Document
import java.time.LocalTime
import java.time.ZoneId

class JourneyService(private val journeyOriginDao: JourneyOriginDao) {

    fun findOriginByLocation(location: String): List<Document> {
        return journeyOriginDao.findJourneyOriginByLocation(location).toList()
    }

    fun findNJourneysFromNow(location: String, numOfJourneys: Int): List<Document> {
        return journeyOriginDao.findJourneyOriginByLocation(location)
            .filter(this::departureTimeAfterNow)
            .take(numOfJourneys)
    }

    private fun departureTimeAfterNow(doc: Document): Boolean {
        val depTime = doc.getDate("departureTime").toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
        return depTime.isAfter(LocalTime.now())
    }
}