package com.shakespace.firstlinecode.chapter09service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.global.TAG

class CommonService : Service() {

    val notificationManager = lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    class DownloadBinder : Binder() {
        fun startDownload() {
            Log.e(this.TAG, "startDownload: ")
        }

        fun getProgress(): Int {

            return 0
        }
    }


    override fun onBind(intent: Intent): IBinder {

        Log.e(this.TAG, "onBind: ")

        return DownloadBinder()
    }

    // call only when first created
    override fun onCreate() {
        super.onCreate()
        Log.e(this.TAG, "onCreate: ")


        /**
         * create a foreground setvice
         */

        val pendingIntent = PendingIntent.getActivity(this, 0, Intent(this, ServiceActivity::class.java), 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("service", "first_code", NotificationManager.IMPORTANCE_LOW)

            notificationManager.value.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, "first_code")
            //  xml(vector) is not acceptable here , only png or jpg
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.notification))
            .setSmallIcon(android.R.drawable.stat_notify_chat)  //  saw in status bar
            .setContentTitle("A new message")
            .setContentText("welcome to Gamble Service")
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        startForeground(1, notification)


    }


    // call everytime when start
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(this.TAG, "onStartCommand: ")
        return super.onStartCommand(intent, flags, startId)
    }


    override fun unbindService(conn: ServiceConnection) {
        super.unbindService(conn)
        Log.e(this.TAG, "unbindService: ")
    }


    override fun onUnbind(intent: Intent?): Boolean {
        Log.e(this.TAG, "onUnbind: ")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(this.TAG, "onDestroy: ")
    }
}
