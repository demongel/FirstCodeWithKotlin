package com.shakespace.firstlinecode.chapter09service.bestpratice

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.global.loge
import com.shakespace.firstlinecode.global.showToast
import java.io.File

@Suppress("DEPRECATION")
class DownloadService : Service() {

    private var downloadTask: DownloadTask? = null
    private var downloadUrl = ""
    private var downloadBinder = DownloadBinder()
    private var status = -1

    override fun onBind(intent: Intent): IBinder {
        return downloadBinder
    }

    // a callback
    private var iDownload = object : IDownload {
        override fun onProgress(progress: Int) {
            getNotificationManager().notify(1, getNotification("Downloading...", progress))
        }

        override fun onSuccess() {
            downloadTask = null
            // close foreground notification
            stopForeground(true)
            getNotificationManager().notify(1, getNotification("Download Success", -1))
            showToast("Download Success")
            status = TYPE_SUCCESS
        }

        override fun onFailed() {
            downloadTask = null
            stopForeground(true)
            getNotificationManager().notify(1, getNotification("Download Failed", -1))
            showToast("Download Failed")
            status = TYPE_FAILED
        }

        override fun onPause() {
            downloadTask = null
            showToast("Paused")
            status = TYPE_PAUSED
        }

        override fun onCancled() {
            downloadTask = null
            showToast("Paused")
            status = TYPE_CANCELED
        }

        override fun onExisted() {
            showToast("File Existed")
            stopForeground(true)
            status = TYPE_EXISTED
        }
    }


    // interact with activity
    inner class DownloadBinder : Binder() {
        fun startDownload(url: String) {
            downloadUrl = url
            if (downloadTask == null) {
                downloadTask = DownloadTask(iDownload)
                downloadTask?.execute(downloadUrl)
                // to be a foreground service 
                startForeground(1, getNotification("Dowanloading...", 0))
                showToast("Downloading...")
            } else {
                downloadTask = DownloadTask(iDownload)
                downloadTask?.execute(downloadUrl)
                showToast("Start again")
            }
        }

        fun pauseDownload() {
            downloadTask?.pauseDownload()
            showToast("Paused")
        }

        //  cancel must check if file has download complete
        fun cancelDownload() {
            if (downloadTask != null) {
                downloadTask?.cancelDownload()
                showToast("Canceled")
                stopForeground(true)
            } else {
                if (status == TYPE_FAILED || status == TYPE_PAUSED) {
                    var fileName = downloadUrl.substringAfterLast("/")
                    var dir =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .path

                    var file = File(dir + fileName)
                    if (file.exists()) {
                        file.delete()
                    }
                    getNotificationManager().cancel(1)
                    stopForeground(true)
                    Toast.makeText(this@DownloadService, "Canceled", Toast.LENGTH_SHORT).show()
                } else {
                    showToast("File already download completed")
                }
            }
        }
    }

    fun getNotificationManager(): NotificationManager {
        return getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    // create a notification
    fun getNotification(title: String, progress: Int): Notification {
        // for android 8.0 , need add channelID
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel("download", "first_code", NotificationManager.IMPORTANCE_LOW)
            channel.enableLights(true)
            channel.lightColor = R.color.green
            //  will show number on you app icon
            channel.setShowBadge(true)
            channel.lockscreenVisibility = 1000
            //  if you have set IMPORTANCE_HIGH , this will not working
            // if you have set pendingIntent+ IMPORTANT_DEFAULT, not working
            channel.enableVibration(false)
            channel.shouldVibrate()

            getNotificationManager().createNotificationChannel(channel)
        }
        val intent = Intent(this, DownloadActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        // this ID must same as above
        val builder = NotificationCompat.Builder(this, "download")
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle(title)
        builder.setContentIntent(pendingIntent)
        if (progress > 0) {
            builder.setContentText("$progress%")
            builder.setProgress(100, progress, false)
        }
        return builder.build()
    }

    override fun onDestroy() {
        super.onDestroy()
        loge("call destroy")
    }
}
