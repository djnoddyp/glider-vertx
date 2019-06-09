package com.pnodder.glidervertx

import com.pnodder.glidervertx.data.DatabaseVerticle
import io.vertx.core.AbstractVerticle
import io.vertx.core.DeploymentOptions
import io.vertx.core.Future

class MainVerticle : AbstractVerticle() {

    override fun start(startFuture: Future<Void>) {
        val httpVerticleDeployment = Future.future<String>()

        vertx.deployVerticle(DatabaseVerticle()) // db verticle starts synchronously
        vertx.deployVerticle("com.pnodder.glidervertx.web.HttpServerVerticle",
            DeploymentOptions().setInstances(2), httpVerticleDeployment)

        httpVerticleDeployment.setHandler { ar ->
            if (ar.succeeded()) {
                startFuture.complete()
            } else {
                startFuture.fail(ar.cause())
            }
        }
    }
}