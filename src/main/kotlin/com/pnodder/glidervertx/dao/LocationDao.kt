package com.pnodder.glidervertx.dao

import com.google.inject.Inject
import com.mongodb.client.MongoCollection
import com.pnodder.glidervertx.LocationCollection
import com.pnodder.glidervertx.domain.Location
import org.bson.Document

class LocationDao @Inject constructor(@LocationCollection val collection: MongoCollection<Document>) : BaseDao<Location> {

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
}