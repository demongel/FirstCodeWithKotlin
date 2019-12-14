package com.shakespace.firstlinecode.chapter13weather.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * created by  shakespace
 * 2019/12/14  17:31
 */
object WeatherApi {
    private const val BASE_URL = "http://guolin.tech/"

    fun <T> create(clazz: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            /**
             * support Deferred
             */
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(clazz)
    }

}