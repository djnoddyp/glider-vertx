package com.pnodder.glidervertx.dao

interface BaseDao<T> {

    fun find(t: T)

    fun insert(t: T)

    fun update(t: T)

    fun delete(t: T)

}