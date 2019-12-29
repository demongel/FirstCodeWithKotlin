package com.shakespace.firstlinecode.chapter13weather.network

/**
 * created by  shakespace
 * 2019/12/14  17:36
 */
class PlaceNetwork {
    
    private val placeService = WeatherApi.create(PlaceService::class.java)

    suspend fun fetchProvinceList() = placeService.getProvinces().await()
    suspend fun fetchCityList(provinceId: Int) = placeService.getCities(provinceId).await()
    suspend fun fetchCountyList(provinceId: Int, cityId: Int) =
        placeService.getCounties(provinceId, cityId).await()

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: PlaceNetwork? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: PlaceNetwork().also { instance = it }
            }
    }


}