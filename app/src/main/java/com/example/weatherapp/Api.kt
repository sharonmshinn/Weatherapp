package com.example.weatherapp


import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather")
    suspend fun getCurrentConditions(
        @Query("zip") zip: String,
        @Query("appid") appId: String = "5548515cd189dd95ffbb311a74da86ae",
        @Query("units") units: String = "imperial",
    ) : CurrentConditions

    @GET("forecast/daily")
    suspend fun getForecast(
        @Query("zip") zip: String,
        @Query("appid") appId: String = "5548515cd189dd95ffbb311a74da86ae",
        @Query("units") units: String = "imperial",
        @Query("cnt") count: String = "16",
    ) : Forecast

}