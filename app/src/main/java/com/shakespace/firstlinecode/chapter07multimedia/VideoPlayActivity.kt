package com.shakespace.firstlinecode.chapter07multimedia

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.global.showToast
import kotlinx.android.synthetic.main.activity_video_play.*
import java.io.File

@Suppress("DEPRECATION")
class VideoPlayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            initVideoPath()
        }

        initListener()
    }

    private fun initListener() {

        tv_play.setOnClickListener {
            if (!video.isPlaying) {
                video.start()
            }
        }

        tv_pause.setOnClickListener {
            if (video.isPlaying) {
                video.pause()
            }
        }

        tv_replay.setOnClickListener {
            if (video.isPlaying) {
                video.resume()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        video.suspend()
    }

    private fun initVideoPath() {
        val file = File(Environment.getExternalStorageDirectory(), "movie.mp4")
        video.setVideoPath(file.path)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initVideoPath()
                } else {
                    showToast("do not have permission")
                }
            }
            else -> " "
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
