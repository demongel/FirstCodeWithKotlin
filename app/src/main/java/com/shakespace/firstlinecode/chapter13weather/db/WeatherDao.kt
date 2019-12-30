package com.shakespace.firstlinecode.chapter13weather.db

import android.preference.PreferenceManager
import com.google.gson.Gson
import com.shakespace.firstlinecode.App
import com.shakespace.firstlinecode.chapter13weather.model.HeWeather

/**
 * created by  shakespace
 * 2019/12/27  23:09
 */
class WeatherDao {

    fun getCacheWeatherInfo(weatherId: String): HeWeather? {
        val weatherInfo =
            PreferenceManager.getDefaultSharedPreferences(App.context)
                .getString(weatherId, null)
        if (weatherInfo != null) {
            return Gson().fromJson(weatherInfo, HeWeather::class.java)
        }
        return null
    }

    fun cacheWeatherInfo(heWeather: HeWeather, weatherId: String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(App.context)
        preferences.edit().putString(weatherId, Gson().toJson(heWeather)).apply()
    }

    fun getCacheBingPicUrl(): String? {
        return PreferenceManager.getDefaultSharedPreferences(App.context)
            .getString("bing_pic", null)
    }

    fun cacheBingPicUrl(url: String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(App.context)
        preferences.edit().putString("bing_pic", url).apply()
    }


    companion object {
        @Volatile
        private var instance: WeatherDao? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: WeatherDao().also { instance = it }
            }
    }
}