package com.example.weatherapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForecastTemp(
    val day: Float,
    val min: Float,
    val max: Float
    ) : Parcelable