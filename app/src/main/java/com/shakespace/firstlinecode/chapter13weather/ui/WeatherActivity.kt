package com.shakespace.firstlinecode.chapter13weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.R
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

    }

    override fun onBackPressed() {
        try {
            // might check visibility?
            if ((area_fragment as AreaChooseFragment).handleBackPress()) {
                return
            }
        } catch (e: Exception) {
        }
        super.onBackPressed()
    }
}
