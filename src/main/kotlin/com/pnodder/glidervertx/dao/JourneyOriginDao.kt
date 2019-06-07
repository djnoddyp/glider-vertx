//package com.pnodder.glidervertx.dao
//
//import com.google.inject.Inject
//import com.mongodb.client.FindIterable
//import com.mongodb.client.MongoCollection
//import com.mongodb.client.model.Filters.eq
//import com.pnodder.glidervertx.JourneyOrigCollection
//import com.pnodder.glidervertx.LocationCollection
//import com.pnodder.glidervertx.domain.JourneyOrigin
//import io.vertx.core.json.JsonArray
//import org.bson.Document
//
//class JourneyOriginDao @Inject constructor(@JourneyOrigCollection val collection: MongoCollection<Document>) : BaseDao<JourneyOrigin> {
//
//    override fun find(t: JourneyOrigin) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun insert(t: JourneyOrigin) {
//        val doc = Document("location", t.location)
//            .append("departureTime", t.departureTime)
//            .append("stopIdentifier", t.stopIdentifier)
//            .append("timingPointIndicator", t.timingPointIndicator)
//            .append("fareStageIndicator", t.fareStageIndicator)
//        collection.insertOne(doc)
//    }
//
//    override fun update(t: JourneyOrigin) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun delete(t: JourneyOrigin) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    fun findJourneyOriginByLocation(location: String): FindIterable<Document> = collection.find(eq("location", location))
//
//}