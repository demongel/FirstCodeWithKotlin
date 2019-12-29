package com.shakespace.firstlinecode.chapter13weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.R

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, AreaChooseFragment.newInstance(), "area")
            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            .commit()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag("area")
        if (fragment is AreaChooseFragment && fragment.isVisible) {
            if (fragment.handleBackPress()) {
                return
            }
        }
        super.onBackPressed()
    }

    fun navigateToWeatherFragment(weatherId: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, WeatherFragment.newInstance(weatherId), "weather")
            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            .commit()
    }
}
