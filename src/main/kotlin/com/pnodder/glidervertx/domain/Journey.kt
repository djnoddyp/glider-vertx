package com.pnodder.glidervertx.domain

import com.pnodder.glidervertx.enums.TransactionType
import java.time.LocalDate

data class Journey(val transactionType: TransactionType,
                   val operator: String,
                   val identifier: String,
                   val firstDateOfOperation: LocalDate,
                   val lastDateOfOperation: LocalDate,
                   val onMondays: Int,
                   val onTuesdays: Int,
                   val onWednesdays: Int,
                   val onThursdays: Int,
                   val onFridays: Int,
                   val onSaturdays: Int,
                   val onSundays: Int,
                   val schoolTermTime: String,
                   val bankHolidays: String,
                   val routeNumber: String,
                   val runningBoard: String,
                   val vehicleType: String,
                   val registrationNumber: String,
                   val routeDirection: String)