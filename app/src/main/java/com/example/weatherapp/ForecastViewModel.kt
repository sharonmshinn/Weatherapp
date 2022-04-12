package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ForecastViewModel @Inject constructor(private val service: Api) : ViewModel() {
    private val _forecast = MutableLiveData<Forecast>()
    val forecast: LiveData<Forecast>
        get() = _forecast

    fun loadData(zipArg: String?) = runBlocking {
        launch {
            _forecast.value = service.getForecast(zipArg!!)
        }
    }
}