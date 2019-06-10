package com.pnodder.glidervertx.services

import com.pnodder.glidervertx.data.JourneyOriginDao
import io.vertx.core.json.JsonArray
import org.bson.Document
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

class JourneyServiceImpl(private val journeyOriginDao: JourneyOriginDao) : JourneyService {

    override fun findJourneyOriginByLocation(location: String): JsonArray {
        return JsonArray(journeyOriginDao.findJourneyOriginByLocation(location).toList())
    }

    override fun findJourneyIntermediateByLocation(location: String): JsonArray {
        return JsonArray(journeyOriginDao.findJourneyOriginByLocation(location).toList())
    }


    fun findNJourneysFromNow(location: String, numOfJourneys: Int): JsonArray {
        // nested function :)
        fun isDepartureTimeAfterNow(doc: Document): Boolean {
            val depTime = doc.getDate("departureTime").toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
            return depTime.isAfter(LocalTime.now())
        }

        return JsonArray(journeyOriginDao.findJourneyOriginByLocation(location)
            .filter(::isDepartureTimeAfterNow)
            .take(numOfJourneys)
            .map { j -> j.getDate("departureTime") })
    }
    
    fun transformDate(date: Date) {
        
    }

}