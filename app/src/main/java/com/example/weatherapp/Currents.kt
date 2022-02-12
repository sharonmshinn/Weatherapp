package com.example.weatherapp
import com.squareup.moshi.Json

data class Currents (
    val temp: Float,
    @Json(name = "feels_like") val feelsLike: Float,
    @Json(name = "temp_min") val tempMin: Float,
    @Json(name = "temp_max") val tempMax: Float,
    val pressure: Float,
    val humidity: Float,

)