package com.pnodder.glidervertx

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.pnodder.glidervertx.dao.RouteDao
import org.bson.Document

class TimetableModule : AbstractModule() {

    private val mongoClient = MongoClient("localhost", 27017)
    private val db: MongoDatabase
    
    init {
        db = mongoClient.getDatabase("timetabledb")
    }

    override fun configure() {
        //bind(RouteDao::class.java).to(RouteDao::class.java)
    }
    
    @Provides @RouteCollection
    fun provideRouteCollection(): MongoCollection<Document> {
        return db.getCollection("route")
    }    
    
    @Provides @JourneyCollection
    fun provideJourneyCollection(): MongoCollection<Document> {
        return db.getCollection("journey")
    }    
    
    @Provides @JourneyDestCollection
    fun provideJourneyDestCollection(): MongoCollection<Document> {
        return db.getCollection("journey_destination")
    }    
    
    @Provides @JourneyIntCollection
    fun provideJourneyIntCollection(): MongoCollection<Document> {
        return db.getCollection("journey_intermediate")
    }    
    
    @Provides @JourneyOrigCollection
    fun provideJourneyOrigCollection(): MongoCollection<Document> {
        return db.getCollection("journey_origin")
    }    
    
    @Provides @LocationCollection
    fun provideLocationCollection(): MongoCollection<Document> {
        return db.getCollection("location")
    }
}

