package com.example.weatherapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecast (
    val list: List<DayForecast>,
    ) : Parcelable
