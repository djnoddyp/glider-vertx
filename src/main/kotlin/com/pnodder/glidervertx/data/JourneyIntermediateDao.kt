//package com.pnodder.glidervertx.data
//
//import com.google.inject.Inject
//import com.mongodb.client.MongoCollection
//import com.mongodb.client.model.Filters.eq
//import com.pnodder.glidervertx.JourneyIntCollection
//import com.pnodder.glidervertx.domain.JourneyIntermediate
//import org.bson.Document
//
//class JourneyIntermediateDao @Inject constructor(@JourneyIntCollection val collection: MongoCollection<Document>) : BaseDao<JourneyIntermediate> {
//
//    override fun find(t: JourneyIntermediate) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun insert(t: JourneyIntermediate) {
//        val doc = Document("location", t.location)
//            .append("arrivalTime", t.arrivalTime)
//            .append("departureTime", t.departureTime)
//            .append("bayNumber", t.bayNumber)
//            .append("timingPointIndicator", t.timingPointIndicator)
//            .append("fareStageIndicator", t.fareStageIndicator)
//        collection.insertOne(doc)
//    }
//
//    override fun update(t: JourneyIntermediate) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun delete(t: JourneyIntermediate) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//    
//    fun findByLocation(location: String) = collection.find(eq("location", location))
//        
//        
//}