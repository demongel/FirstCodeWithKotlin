package com.shakespace.firstlinecode.chapter13weather.model

import com.google.gson.annotations.SerializedName

data class Weather(
    val HeWeather: List<HeWeather>
)

data class HeWeather(
    val aqi: Aqi,
    val basic: Basic,
    val daily_forecast: List<DailyForecast>,
    val msg: String,
    val now: Now,
    val status: String,
    val suggestion: Suggestion,
    val update: UpdateX
)

data class Aqi(
    @SerializedName("city")
    val city: AQICity
)

data class AQICity(
    val aqi: String,
    val pm25: String,
    val qlty: String
)

data class Basic(
    val admin_area: String,
    val cid: String,
    val city: String,
    val cnty: String,
    val id: String,
    val lat: String,
    val location: String,
    val lon: String,
    val parent_city: String,
    val tz: String,
    val update: Update
)

data class Update(
    val loc: String,
    val utc: String
)

data class DailyForecast(
    val cond: Cond,
    val date: String,
    val tmp: Tmp
)

data class Cond(
    val txt_d: String
)

data class Tmp(
    val max: String,
    val min: String
)

data class Now(
    val cloud: String,
    val cond: CondX,
    val cond_code: String,
    val cond_txt: String,
    val fl: String,
    val hum: String,
    val pcpn: String,
    val pres: String,
    val tmp: String,
    val vis: String,
    val wind_deg: String,
    val wind_dir: String,
    val wind_sc: String,
    val wind_spd: String
)

data class CondX(
    val code: String,
    val txt: String
)

data class Suggestion(
    val comf: Comf,
    val cw: Cw,
    val sport: Sport
)

data class Comf(
    val brf: String,
    val txt: String,
    val type: String
)

data class Cw(
    val brf: String,
    val txt: String,
    val type: String
)

data class Sport(
    val brf: String,
    val txt: String,
    val type: String
)

data class UpdateX(
    val loc: String,
    val utc: String
)