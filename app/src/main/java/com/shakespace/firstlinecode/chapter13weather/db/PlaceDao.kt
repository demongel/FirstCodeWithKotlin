package com.shakespace.firstlinecode.chapter13weather.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shakespace.firstlinecode.chapter13weather.model.City
import com.shakespace.firstlinecode.chapter13weather.model.County
import com.shakespace.firstlinecode.chapter13weather.model.Province

/**
 * created by  shakespace
 * 2019/12/14  17:04
 */

@Dao
interface PlaceDao {
    @Query("SELECT * FROM province ORDER BY provinceCode")
    fun getProvinces(): List<Province>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProvinces(provinces: List<Province>)

    @Query("SELECT * FROM city where provinceId = :provinceCode ORDER BY cityCode")
    fun getCities(provinceCode: Int): List<City>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCities(cities: List<City>)

    @Query("SELECT * FROM county  where cityId = :cityCode ORDER BY countyName")
    fun getCounties(cityCode: Int): List<County>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCounties(counties: List<County>)
}