package com.shakespace.firstlinecode.chapter07multimedia

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.R


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_play)

        val player = MediaPlayer().apply {

        }

    }
}
