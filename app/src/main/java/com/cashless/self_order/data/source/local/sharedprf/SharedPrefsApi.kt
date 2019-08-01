package com.cashless.self_order.data.source.local.sharedprf

interface SharedPrefsApi {
    fun <T> get(key: String, clazz: Class<T>): T

    fun <T> put(key: String, data: T)

    fun clear()

    fun clearKey(key: String)
}