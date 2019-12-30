package com.shakespace.firstlinecode.chapter13weather.db

import com.shakespace.firstlinecode.chapter13weather.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * created by  shakespace
 * 2019/12/27  23:09
 */

class WeatherRepository(val dao: WeatherDao, val network: WeatherNetwork) {


    suspend fun fetchWeather(weatherId: String) = withContext(Dispatchers.IO) {
        var heWeather = dao.getCacheWeatherInfo(weatherId)
        if (heWeather == null) {
            val weather = network.fetchWeather(weatherId)
            heWeather = weather.HeWeather[0]
            dao.cacheWeatherInfo(heWeather, weatherId)
        }
        heWeather
    }

    suspend fun refreshWeather(weatherId: String) = withContext(Dispatchers.IO) {
        network.fetchWeather(weatherId)
    }

    suspend fun fetchBingPic(): String? = withContext(Dispatchers.IO) {
        var picUrl = dao.getCacheBingPicUrl()
        if (picUrl == null) {
            picUrl = network.fetchBingPicUrl()
            dao.cacheBingPicUrl(picUrl)
        }
        picUrl
    }


    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: WeatherRepository? = null

        fun getInstance(dao: WeatherDao, network: WeatherNetwork) =
            instance ?: synchronized(this) {
                instance ?: WeatherRepository(dao, network).also { instance = it }
            }
    }

}