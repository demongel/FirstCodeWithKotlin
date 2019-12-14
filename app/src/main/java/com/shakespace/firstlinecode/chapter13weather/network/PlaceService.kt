package com.shakespace.firstlinecode.chapter13weather.network

import com.shakespace.firstlinecode.chapter13weather.model.City
import com.shakespace.firstlinecode.chapter13weather.model.County
import com.shakespace.firstlinecode.chapter13weather.model.Province
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaceService {

    @GET("api/china")
    fun getProvinces(): Deferred<List<Province>>

    @GET("api/china/{provinceId}")
    fun getCities(@Path("provinceId") provinceId: Int): Deferred<List<City>>

    @GET("api/china/{provinceId}/{cityId}")
    fun getCounties(@Path("provinceId") provinceId: Int, @Path("cityId") cityId: Int): Deferred<List<County>>
}