package com.shakespace.firstlinecode.chapter13weather.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * created by  shakespace
 * 2019/12/14  16:48
 */

@Entity(tableName = "province")
data class Province(
    @SerializedName("name") val provinceName: String, @PrimaryKey @SerializedName("id") val provinceCode: Int
) {
    @Transient
    val id = 0
}
