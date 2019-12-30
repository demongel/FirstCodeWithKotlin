package com.shakespace.firstlinecode.chapter13weather.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shakespace.firstlinecode.chapter13weather.call
import com.shakespace.firstlinecode.chapter13weather.db.WeatherRepository
import com.shakespace.firstlinecode.chapter13weather.model.HeWeather
import com.shakespace.firstlinecode.global.loge

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    var weatherInfo = MutableLiveData<HeWeather>()
    var bingPicUrl = MutableLiveData<String>()
    var error = MutableLiveData<String>()

    fun getWeather(weatherId: String) = call({
        weatherInfo.value = repository.fetchWeather(weatherId)
    }, {
        error.value = it.message
    }
    )

    fun loadBingPic() = call({
        val bingPic = repository.fetchBingPic()
        loge(bingPic + "-----")
    }, {
        error.value = it.localizedMessage
    })

}
