package com.shakespace.firstlinecode.chapter07multimedia

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.global.TAG
import com.shakespace.firstlinecode.global.showToast
import kotlinx.android.synthetic.main.activity_audio_play.*
import java.io.File


/**
 *  setDataSource("")
 *  prepare()
 *  start()
 *  pause()
 *  reset()
 *  seekTo(100)
 *  stop()
 *  release()
 *  isPlaying
 *  duration
 *
 *
 */
class AudioPlayActivity : AppCompatActivity() {

    val player = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_play)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        } else {
            initMediaPlayer()
        }

        initListener()

    }

    private fun initListener() {

        tv_play.setOnClickListener {
            if (!player.isPlaying) {
                player.start()
            }
        }

        tv_pause.setOnClickListener {
            if (player.isPlaying) {
                player.pause()
            }
        }

        tv_stop.setOnClickListener {
            if (player.isPlaying) {
                player.reset()
                initMediaPlayer()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (player != null) {
            player.stop()
            player.release()
        }
    }

    private fun initMediaPlayer() {
        try {
            val file = File(Environment.getExternalStorageDirectory(), "movie.mp4")
            player.setDataSource(file.path)
            player.prepare()
        } catch (e: Exception) {
            Log.e(this.TAG, "initMediaPlayer: ${e.localizedMessage}")
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initMediaPlayer()
                } else {
                    showToast("do not have permission")
                }
            }
            else -> " "
        }

    }
}
