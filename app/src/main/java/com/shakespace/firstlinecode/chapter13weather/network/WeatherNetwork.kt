package com.shakespace.firstlinecode.chapter13weather.network

import com.shakespace.firstlinecode.chapter13weather.KEY

/**
 * created by  shakespace
 * 2019/12/14  17:36
 */
class WeatherNetwork {

    private val weatherService = WeatherApi.create(WeatherService::class.java)

    suspend fun fetchWeather(weatherId: String) =
        weatherService.getWeather(weatherId, KEY).await()

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: WeatherNetwork? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: WeatherNetwork().also { instance = it }
            }
    }


}