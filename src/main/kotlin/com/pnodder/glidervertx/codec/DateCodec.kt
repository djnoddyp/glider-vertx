package com.pnodder.glidervertx.codec

import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec
import io.vertx.core.json.JsonObject
import java.time.LocalTime
import java.util.*

class DateCodec : MessageCodec<Date, LocalTime> {

    override fun encodeToWire(buffer: Buffer, s: Date) {
        val instant = JsonObject().put("instant", s.toInstant())
        val instantStr = instant.encode()
        buffer.appendInt(instantStr.length)
        buffer.appendString(instantStr)
    }

    override fun decodeFromWire(pos: Int, buffer: Buffer): LocalTime {
        val length = buffer.getInt(pos)
        val jsonStr = buffer.getString(pos.plus(4), pos.plus(length))
        val contentJson = JsonObject(jsonStr)
        val instant = contentJson.getString("instant")
        return LocalTime.parse(instant)
    }

    override fun systemCodecID(): Byte {
        return -1
    }

    override fun transform(s: Date?): LocalTime {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun name() = javaClass.simpleName
}