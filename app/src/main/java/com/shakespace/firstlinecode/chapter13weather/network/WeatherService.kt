package com.shakespace.firstlinecode.chapter13weather.network

import com.shakespace.firstlinecode.chapter13weather.model.Weather
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("api/weather")
    fun getWeather(@Query("cityid") weatherId: String, @Query("key") key: String): Deferred<Weather>

    @GET("api/bing_pic")
    fun getBingPic(): Deferred<String>

}