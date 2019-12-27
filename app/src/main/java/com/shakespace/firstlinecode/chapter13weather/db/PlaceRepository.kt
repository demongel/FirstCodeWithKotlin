package com.shakespace.firstlinecode.chapter13weather.db

import com.shakespace.firstlinecode.chapter13weather.model.City
import com.shakespace.firstlinecode.chapter13weather.model.County
import com.shakespace.firstlinecode.chapter13weather.model.Province
import com.shakespace.firstlinecode.chapter13weather.network.PlaceNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * created by  shakespace
 * 2019/12/14  17:10
 */
class PlaceRepository private constructor(
    private val placeDao: PlaceDao,
    private val placeNetwork: PlaceNetwork
) {

    private lateinit var provinces: List<Province>
    private lateinit var cities: List<City>
    private lateinit var counties: List<County>

    //  still get LiveData
    suspend fun getProvinces() = withContext(Dispatchers.IO) {
        provinces = placeDao.getProvinces()
        if (provinces.isNullOrEmpty()) {
            val provinceList = placeNetwork.fetchProvinceList()
            saveProvinces(provinceList)
        }
        provinces
    }

    suspend fun saveProvinces(provinces: List<Province>) = withContext(Dispatchers.IO) {
        placeDao.saveProvinces(provinces)
    }

    suspend fun getCities(provinceId: Int) = withContext(Dispatchers.IO) {
        cities = placeDao.getCities(provinceId)
        if (cities.isNullOrEmpty()) {
            cities = placeNetwork.fetchCityList(provinceId).also {
                it.forEach { city ->
                    city.provinceId = provinceId
                }
            }
            saveCities(cities)
        }
        cities
    }

    suspend fun saveCities(cities: List<City>) = withContext(Dispatchers.IO) {
        placeDao.saveCities(cities)
    }

    suspend fun getCountyies(provinceId: Int, cityId: Int) = withContext(Dispatchers.IO) {
        counties = placeDao.getCounties(cityId)
        if (counties.isNullOrEmpty()) {
            counties = placeNetwork.fetchCountyList(provinceId, cityId).also {
                it.forEach { county ->
                    county.cityId = cityId
                }
            }
            saveCounties(counties)
        }
        counties
    }

    suspend fun saveCounties(counties: List<County>) = withContext(Dispatchers.IO) {
        placeDao.saveCounties(counties)
    }


    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: PlaceRepository? = null

        fun getInstance(placeDao: PlaceDao, placeNetwork: PlaceNetwork) =
            instance ?: synchronized(this) {
                instance ?: PlaceRepository(placeDao, placeNetwork).also { instance = it }
            }
    }
}
