//package com.pnodder.glidervertx.services
//
//import com.pnodder.glidervertx.dao.JourneyOriginDao
//import org.bson.Document
//import java.time.LocalTime
//import java.time.ZoneId
//
//class JourneyService(private val journeyOriginDao: JourneyOriginDao) {
//
//    fun findJourneyOriginByLocation(location: String): List<Document> {
//        return journeyOriginDao.findJourneyOriginByLocation(location).toList()
//    }
//
//    fun findJourneyIntermediateByLocation(location: String): List<Document> {
//        return journeyOriginDao.findJourneyOriginByLocation(location).toList()
//    }
//
//
//    fun findNJourneysFromNow(location: String, numOfJourneys: Int): List<Document> {
//        // nested function :)
//        fun isDepartureTimeAfterNow(doc: Document): Boolean {
//            val depTime = doc.getDate("departureTime").toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
//            return depTime.isAfter(LocalTime.now())
//        }
//
//        return journeyOriginDao.findJourneyOriginByLocation(location)
//            .filter(::isDepartureTimeAfterNow)
//            .take(numOfJourneys)
//    }
//
//}