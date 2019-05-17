package com.pnodder.glidervertx.domain

import java.time.LocalTime

data class JourneyDestination(
    val location: String,
    val arrivalTime: LocalTime,
    val bayNumber: String,
    val timingPointIndicator: String, val fareStageIndicator: String
)