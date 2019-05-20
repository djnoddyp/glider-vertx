package com.pnodder.glidervertx

import com.google.inject.Inject
import com.pnodder.glidervertx.dao.*
import com.pnodder.glidervertx.domain.*
import com.pnodder.glidervertx.enums.TransactionType
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.system.measureTimeMillis


class TimetableReader @Inject constructor(
    val routeDao: RouteDao,
    val journeyDao: JourneyDao,
    val journeyOriginDao: JourneyOriginDao,
    val journeyIntermediateDao: JourneyIntermediateDao,
    val journeyDestinationDao: JourneyDestinationDao,
    val locationDao: LocationDao
) {

    private val timePattern = DateTimeFormatter.ofPattern("HHmm")
    private val datePattern = DateTimeFormatter.ofPattern("yyyyMMdd")

    fun read() {
        val duration = measureTimeMillis {
            // TODO use coroutines to run in parallel
            getTimetableData().bufferedReader().readLines().stream().filter { l -> l.startsWith("QD") }.forEach(::routeReader)
            getTimetableData().bufferedReader().readLines().stream().filter { l -> l.startsWith("QS") }.forEach(::journeyReader)
            getTimetableData().bufferedReader().readLines().stream().filter { l -> l.startsWith("QO") }.forEach(::journeyOriginReader)
            getTimetableData().bufferedReader().readLines().stream().filter { l -> l.startsWith("QI") }.forEach(::journeyIntermediateReader)
            getTimetableData().bufferedReader().readLines().stream().filter { l -> l.startsWith("QT") }.forEach(::journeyDestinationReader)
            getTimetableData().bufferedReader().readLines().stream().filter { l -> l.startsWith("QL") }.forEach(::locationReader)
        }
        println("Timetable parsed in ${duration}ms")
    }

    private fun getTimetableData() = TimetableReader::class.java.classLoader.getResourceAsStream("glider_data.cif")

    // TODO store entities in a list and then insert all, save many db trips
    fun routeReader(line: String) {
        if (line.length == 80) {
            val type = line.substring(2, 3)
            val operator = line.substring(3, 7)
            val number = line.substring(7, 11)
            val direction = line.substring(11, 12)
            val description = line.substring(12, 80)
            val route = Route(TransactionType.valueOf(type), operator, number, direction, description)
            routeDao.insert(route)
        } else {
            println("QD line skipped- not 80 characters long!")
        }
    }

    fun journeyReader(line: String) {
        if (line.length == 65) {
            val type = line.substring(2, 3)
            val operator = line.substring(3, 7)
            val identifier = line.substring(7, 13)
            val firstDate = line.substring(13, 21)
            val lastDate = line.substring(21, 29)
            val mon = line.substring(29, 30)
            val tues = line.substring(30, 31)
            val weds = line.substring(31, 32)
            val thurs = line.substring(32, 33)
            val fri = line.substring(33, 34)
            val sat = line.substring(34, 35)
            val sun = line.substring(35, 36)
            val schoolTerm = line.substring(36, 37)
            val bank = line.substring(37, 38)
            val routeNum = line.substring(38, 42)
            val runningBoard = line.substring(42, 48)
            val vehicleType = line.substring(48, 56)
            val regNum = line.substring(56, 64)
            val routeDirection = line.substring(64, 65)
            val journey = Journey(TransactionType.valueOf(type), operator, identifier, LocalDate.parse(firstDate, datePattern),
                LocalDate.parse(lastDate, datePattern), mon.toInt(), tues.toInt(), weds.toInt(), thurs.toInt(), fri.toInt(),
                sat.toInt(), sun.toInt(), schoolTerm, bank, routeNum, runningBoard, vehicleType, regNum, routeDirection)
            journeyDao.insert(journey)
        } else {
            println("QS line skipped, expected 65 chars got ${line.length}")
        }
    }

    fun journeyOriginReader(line: String) {
        if (line.length == 25) {
            val location = line.substring(2, 14)
            val depTime = line.substring(14, 18)
            val stopId = line.substring(18, 21)
            val timingIndicator = line.substring(21, 23)
            val fareIndicator = line.substring(23, 25)
            val journeyOrigin =
                JourneyOrigin(location, LocalTime.parse(depTime, timePattern), stopId, timingIndicator, fareIndicator)
            journeyOriginDao.insert(journeyOrigin)
        } else {
            println("QO line skipped, expected 25 chars got ${line.length}")
        }
    }

    fun journeyIntermediateReader(line: String) {
        if (line.length == 30) {
            val location = line.substring(2, 14)
            val arrivalTime = line.substring(14, 18)
            val depTime = line.substring(18, 22)
            val activityFlag = line.substring(22, 23)
            val bayNumber = line.substring(23, 26)
            val timingIndicator = line.substring(26, 28)
            val fareIndicator = line.substring(28, 30)
            val journeyIntermediate = JourneyIntermediate(location, LocalTime.parse(arrivalTime, timePattern),
                LocalTime.parse(depTime, timePattern), activityFlag, bayNumber, timingIndicator, fareIndicator)
            journeyIntermediateDao.insert(journeyIntermediate)
        } else {
            println("QI line skipped, expected 30 chars got ${line.length}")
        }
    }

    fun journeyDestinationReader(line: String) {
        if (line.length == 25) {
            val location = line.substring(2, 14)
            val arrivalTime = line.substring(14, 18)
            val bayNumber = line.substring(18, 21)
            val timingIndicator = line.substring(21, 23)
            val fareIndicator = line.substring(23, 25)
            val journeyDestination = JourneyDestination(location, LocalTime.parse(arrivalTime, timePattern), bayNumber,
                timingIndicator, fareIndicator)
            journeyDestinationDao.insert(journeyDestination)
        } else {
            println("QT line skipped, expected 25 chars got ${line.length}")
        }
    }

    fun locationReader(line: String) {
        val type = line.substring(2, 3)
        val loc = line.substring(3, 15)
        val fullLocation = line.substring(15, line.length)
        val location = Location(type, loc, fullLocation)
        locationDao.insert(location)
    }
}


