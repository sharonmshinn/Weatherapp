package com.example.weatherapp


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather")
    fun getCurrentConditions(
        @Query("zip") zip: String,
        @Query("appid") appId: String = "5548515cd189dd95ffbb311a74da86ae",
        @Query("units") units: String = "imperial",
    ) : Call<CurrentConditions>

    @GET("daily")
    fun getForecast(
        @Query("zip") zip: String,
        @Query("appid") appId: String = "5548515cd189dd95ffbb311a74da86ae",
        @Query("units") units: String = "imperial",
        @Query("cnt") count: String = "16",
    ) : Call<Forecast>

}