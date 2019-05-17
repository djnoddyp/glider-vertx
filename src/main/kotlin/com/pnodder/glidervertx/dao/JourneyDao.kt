package com.pnodder.glidervertx.dao

import com.google.inject.Inject
import com.mongodb.client.MongoCollection
import com.pnodder.glidervertx.JourneyCollection
import com.pnodder.glidervertx.domain.Journey
import org.bson.Document

class JourneyDao @Inject constructor(@JourneyCollection val collection: MongoCollection<Document>) : BaseDao<Journey> {

    override fun find(t: Journey) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insert(t: Journey) {
        val doc = Document("transactionType", t.transactionType.description)
            .append("operator", t.operator)
            .append("identifier", t.identifier)
            .append("firstDateOfOperation", t.firstDateOfOperation)
            .append("lastDateOfOperation", t.lastDateOfOperation)
            .append("onMondays", t.onMondays)
            .append("onTuesdays", t.onTuesdays)
            .append("onWednesdays", t.onWednesdays)
            .append("onThursdays", t.onThursdays)
            .append("onFridays", t.onFridays)
            .append("onSaturdays", t.onSaturdays)
            .append("onSundays", t.onSundays)
            .append("schoolTermTime", t.schoolTermTime)
            .append("bankHolidays", t.bankHolidays)
            .append("routeNumber", t.routeNumber)
            .append("runningBoard", t.runningBoard)
            .append("vehicleType", t.vehicleType)
            .append("registrationNumber", t.registrationNumber)
            .append("routeDirection", t.routeDirection)
        collection.insertOne(doc)
    }

    override fun update(t: Journey) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(t: Journey) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}