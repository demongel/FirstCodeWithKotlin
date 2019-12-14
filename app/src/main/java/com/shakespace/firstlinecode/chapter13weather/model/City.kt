package com.shakespace.firstlinecode.chapter13weather.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * created by  shakespace
 * 2019/12/14  16:53
 */
@Entity(tableName = "city")
data class City(
    @SerializedName("name") val cityName: String, @PrimaryKey @SerializedName("id") val cityCode: Int
) {
    @Transient
    val id = 0
    var provinceId = 0
}