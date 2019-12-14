package com.shakespace.firstlinecode.chapter13weather.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * created by  shakespace
 * 2019/12/14  17:00
 */

@Entity(tableName = "county")
data class County(
    @SerializedName("name") val countyName: String, @PrimaryKey @SerializedName("weather_id") val weatherId: String
) {
    @Transient
    val id = 0
    var cityId = 0
}