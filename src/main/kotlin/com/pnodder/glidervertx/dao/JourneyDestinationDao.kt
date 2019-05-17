package com.pnodder.glidervertx.dao

import com.google.inject.Inject
import com.mongodb.client.MongoCollection
import com.pnodder.glidervertx.JourneyDestCollection
import com.pnodder.glidervertx.domain.JourneyDestination
import org.bson.Document

class JourneyDestinationDao @Inject constructor(@JourneyDestCollection val collection: MongoCollection<Document>) : BaseDao<JourneyDestination> {

    override fun find(t: JourneyDestination) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insert(t: JourneyDestination) {
        val doc = Document("location", t.location)
            .append("arrivalTime", t.arrivalTime)
            .append("bayNumber", t.bayNumber)
            .append("timingPointIndicator", t.timingPointIndicator)
        collection.insertOne(doc)
    }

    override fun update(t: JourneyDestination) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(t: JourneyDestination) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}