package com.pnodder.glidervertx.data

interface BaseDao<T> {

    fun find(t: T)

    fun insert(t: T)

    fun update(t: T)

    fun delete(t: T)

}