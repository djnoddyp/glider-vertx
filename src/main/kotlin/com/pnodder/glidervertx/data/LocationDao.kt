package com.pnodder.glidervertx.data

import com.google.inject.Inject
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Projections.excludeId
import com.mongodb.client.model.Sorts.ascending
import com.pnodder.glidervertx.domain.Location
import org.bson.Document

class LocationDao @Inject constructor(database: MongoDatabase) : BaseDao<Location> {

    private val collection = database.getCollection("location")

    override fun find(t: Location) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insert(t: Location) {
        val doc = Document("transactionType", t.transactionType)
            .append("location", t.location)
            .append("fullLocation", t.fullLocation)
        collection.insertOne(doc)
    }

    override fun update(t: Location) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(t: Location) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun findByFullLocation(fullLocation: String): FindIterable<Document> {
        return collection.find(eq("fullLocation", fullLocation))
    }

    fun findAll(): FindIterable<Document> {
        return collection.find(Document())
            .projection(excludeId())
            .sort(ascending("fullLocation"))
    }

}