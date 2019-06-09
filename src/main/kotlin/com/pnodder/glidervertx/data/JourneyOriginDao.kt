package com.pnodder.glidervertx.data

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Projections.excludeId
import com.pnodder.glidervertx.domain.JourneyOrigin
import org.bson.Document

class JourneyOriginDao(mongoDatabase: MongoDatabase) : BaseDao<JourneyOrigin> {

    private val collection = mongoDatabase.getCollection("journey_origin")

    override fun find(t: JourneyOrigin) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insert(t: JourneyOrigin) {
        val doc = Document("location", t.location)
            .append("departureTime", t.departureTime)
            .append("stopIdentifier", t.stopIdentifier)
            .append("timingPointIndicator", t.timingPointIndicator)
            .append("fareStageIndicator", t.fareStageIndicator)
        collection.insertOne(doc)
    }

    override fun update(t: JourneyOrigin) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(t: JourneyOrigin) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun findJourneyOriginByLocation(location: String): FindIterable<Document> =
        collection.find(eq("location", location)).projection(excludeId())

}