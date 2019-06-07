//package com.pnodder.glidervertx.dao
//
//import com.google.inject.Inject
//import com.mongodb.client.model.Filters.eq
//import com.mongodb.client.model.Projections.include
//import com.pnodder.glidervertx.domain.Location
//import io.vertx.core.json.JsonObject
//import io.vertx.ext.mongo.MongoClient
//import org.bson.Document
//
//class LocationDao @Inject constructor(val client: MongoClient) : BaseDao<Location> {
//
//    override fun find(t: Location) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun insert(t: Location) {
//        val doc = Document("transactionType", t.transactionType)
//            .append("location", t.location)
//            .append("fullLocation", t.fullLocation)
//        collection.insertOne(doc)
//    }
//
//    override fun update(t: Location) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun delete(t: Location) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//    
//    fun findByFullLocation(fullLocation: String) = collection.find(eq("fullLocation", fullLocation))
//    
//    fun findAllFullLocations() = collection.find(Document()).projection(include("fullLocation"))
//}