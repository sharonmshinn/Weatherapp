package com.example.weatherapp

data class DayForecast(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: ForecastTemp,
    val pressure: Float,
    val humidity: Int,
    val weather: List<WeatherCondition>,
    )