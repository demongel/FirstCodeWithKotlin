package com.shakespace.firstlinecode.chapter09service.bestpratice

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.global.showToast
import kotlinx.android.synthetic.main.activity_download.*

class DownloadActivity : AppCompatActivity() {

    private var downloadBinder: DownloadService.DownloadBinder? = null
    private var downloadIntent: Intent? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            downloadBinder = service as DownloadService.DownloadBinder
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)

        downloadIntent = Intent(this, DownloadService::class.java)
        startService(downloadIntent)
        bindService(downloadIntent, serviceConnection, Context.BIND_AUTO_CREATE)

        // check permission
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }

        start_download.setOnClickListener {
            // Maxthon Browser
            val url = "http://dl.maxthon.cn/mx5/mx5.3.8.2000cn.exe"
            downloadBinder?.startDownload(url)
        }

        pause_download.setOnClickListener {
            downloadBinder?.pauseDownload()
        }

        cancel_download.setOnClickListener {
            downloadBinder?.cancelDownload()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    showToast("拒绝权限将无法下载")
                }
            }
            else -> ""
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
        stopService(downloadIntent)
    }
}
