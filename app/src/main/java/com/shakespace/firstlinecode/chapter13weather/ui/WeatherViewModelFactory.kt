package com.shakespace.firstlinecode.chapter13weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shakespace.firstlinecode.chapter13weather.db.WeatherRepository

/**
 * created by  shakespace
 * 2019/12/14  23:24
 */
@Suppress("UNCHECKED_CAST")
class WeatherViewModelFactory(private val repository: WeatherRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(repository) as T
    }
}