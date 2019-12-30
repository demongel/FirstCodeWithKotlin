package com.shakespace.firstlinecode.chapter13weather.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.preference.PreferenceManager
import com.shakespace.firstlinecode.chapter13weather.db.WeatherDao
import com.shakespace.firstlinecode.chapter13weather.network.WeatherNetwork
import com.shakespace.firstlinecode.global.loge
import kotlinx.coroutines.*

class AutoUpdateService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        MainScope().launch {
            withContext(Dispatchers.IO) {
                delay(2 * 60 * 60 * 1000)
                updateWeather()
                updateBingPic()
                loge("------------------- do update ")
            }
        }

        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val interval = 2 * 60 * 60 * 1000
        // trigger Time
        val triggerAtTime = SystemClock.elapsedRealtime() + interval
        val i = Intent(this, AutoUpdateService::class.java)
        val pendingIntent = PendingIntent.getService(this, 0, i, 0)
        manager.cancel(pendingIntent)
//        which will wake up the device when it goes off
//        在指定的延时过后，发送广播，并唤醒设备（即使关机也会执行operation所对应的组件）。与ELAPSED_REALTIME基本功能一样，只是会在设备休眠时唤醒设备;
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent)

        return super.onStartCommand(intent, flags, startId)
    }

    suspend fun updateWeather() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val weatherId = preferences.getString("weather_id", "")
        if (weatherId.isNullOrEmpty()) {
            // something error
            loge("---- weatherId is null or empty")
        } else {
            val weather = WeatherNetwork.getInstance().fetchWeather(weatherId)
            val heWeather = weather.HeWeather[0]
            WeatherDao.getInstance().cacheWeatherInfo(heWeather, weatherId)
        }
    }

    suspend fun updateBingPic() {
        val picUrl = WeatherNetwork.getInstance().fetchBingPicUrl()
        if (picUrl.isNotEmpty()) {
            WeatherDao.getInstance().cacheBingPicUrl(picUrl)
        }
    }
}
