package com.example.weatherapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherCondition (
    val main: String,
    val icon: String,
) : Parcelable