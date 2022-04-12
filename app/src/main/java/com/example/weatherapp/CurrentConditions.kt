package com.example.weatherapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrentConditions(
    val weather: List<WeatherCondition>,
    val main: Currents,
    val name: String,
) : Parcelable