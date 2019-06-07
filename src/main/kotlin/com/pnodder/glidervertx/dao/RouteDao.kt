//package com.pnodder.glidervertx.dao
//
//import com.google.inject.Inject
//import com.mongodb.async.client.MongoCollection
//import com.mongodb.client.MongoCollection
//import com.pnodder.glidervertx.RouteCollection
//import com.pnodder.glidervertx.domain.Route
//import org.bson.Document
//
//class RouteDao @Inject constructor(@RouteCollection val collection: MongoCollection<Document>) : BaseDao<Route> {
//
//    override fun find(t: Route) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun insert(t: Route) {
//        val doc = Document("transactionType", t.transactionType.description)
//            .append("operator", t.operator)
//            .append("number", t.number)
//            .append("direction", t.direction)
//            .append("description", t.description)
//        collection.insertOne(doc)
//    }
//
//    override fun update(t: Route) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun delete(t: Route) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}