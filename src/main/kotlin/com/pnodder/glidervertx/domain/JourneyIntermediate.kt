package com.pnodder.glidervertx.domain

import java.time.LocalTime

data class JourneyIntermediate(
    val location: String,
    val arrivalTime: LocalTime,
    val departureTime: LocalTime,
    val activityFlag: String,
    val bayNumber: String,
    val timingPointIndicator: String,
    val fareStageIndicator: String
)