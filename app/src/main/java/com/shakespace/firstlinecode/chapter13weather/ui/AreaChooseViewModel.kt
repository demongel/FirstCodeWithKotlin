package com.shakespace.firstlinecode.chapter13weather.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shakespace.firstlinecode.chapter13weather.call
import com.shakespace.firstlinecode.chapter13weather.db.PlaceRepository
import com.shakespace.firstlinecode.chapter13weather.model.City
import com.shakespace.firstlinecode.chapter13weather.model.County
import com.shakespace.firstlinecode.chapter13weather.model.Province
import java.util.*

/**
 * created by  shakespace
 * 2019/12/14  22:30
 */
class AreaChooseViewModel(private val repository: PlaceRepository) : ViewModel() {

    var currentLevel = MutableLiveData<Int>()

    var selectedProvince: Province? = null

    var selectedCity: City? = null

    var provinces = MutableLiveData<List<Province>>()

    var cities = MutableLiveData<List<City>>()

    var counties = MutableLiveData<List<County>>()

    var dataList = ArrayList<String>()

    var error = MutableLiveData<String>()

    fun getProcinces() = call({
        provinces.value = repository.getProvinces()
    }, {
        error.value = it.message
    })

    fun getCities(provinceId: Int) = call({
        cities.value = repository.getCities(provinceId)
    }, {
        error.value = it.message
    })

    fun getCounties(provinceId: Int, cityId: Int) = call({
        counties.value = repository.getCountyies(provinceId, cityId)
    }, {
        error.value = it.message
    })


    //This scope will be canceled when ViewModel will be cleared
//    fun call(success: suspend () -> Unit, fail: suspend (Throwable) -> Unit) =
//        viewModelScope.launch {
//            try {
//                success()
//            } catch (t: Throwable) {
//                fail(t)
//            }
//        }

}