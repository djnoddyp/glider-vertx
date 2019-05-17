package com.pnodder.glidervertx.domain

import com.pnodder.glidervertx.enums.TransactionType

data class Route(
    val transactionType: TransactionType,
    val operator: String,
    val number: String, 
    val direction: String,
    val description: String
)