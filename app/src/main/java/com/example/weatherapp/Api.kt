package com.example.weatherapp

import android.telecom.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api : Call<CurrentConditions{
    @GET("weather")
    fun getCurrentConditions(
        @Query("zip") zip: String,
        @Query("units") unites: String = "imperial",
        @Query("appid") appId: String = "5548515cd189dd95ffbb311a74da86ae",
        ) : Call<CurrentConditions>

}