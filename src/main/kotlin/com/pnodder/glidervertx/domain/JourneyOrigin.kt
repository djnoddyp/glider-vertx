package com.pnodder.glidervertx.domain

import java.time.LocalTime

data class JourneyOrigin(
    val location: String,
    val departureTime: LocalTime,
    val stopIdentifier: String,
    val timingPointIndicator: String,
    val fareStageIndicator: String
)