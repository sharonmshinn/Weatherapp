package com.example.weatherapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val service: Api) : ViewModel() {


    private var zipCode: String? = null
    private val _zipCodeText = MutableLiveData<String>()
    private val _enableButton = MutableLiveData(false)
    private val _showErrorDialog = MutableLiveData(false)
    private val _currentConditions = MutableLiveData<CurrentConditions>()
    private var latitude: String? = null
    private val _latitudeText = MutableLiveData<String>()
    private var longitude: String? = null
    private val _longitudeText = MutableLiveData<String>()


    val currentConditions: LiveData<CurrentConditions>
        get() = _currentConditions

    val showErrorDialog: LiveData<Boolean>
        get() = _showErrorDialog

    val enableButton: LiveData<Boolean>
        get() = _enableButton

    val zipCodeText: LiveData<String>
        get() = _zipCodeText

    val latitudeText: LiveData<String>
        get() = _latitudeText

    val longitudeText: LiveData<String>
        get() = _longitudeText

    fun updateZipCode(zipCode: String) {
        if(zipCode != this.zipCode)
            this.zipCode = zipCode
            _enableButton.value = isValidZipCode(zipCode)
    }

    fun updateLat(lat: String) {
        if(lat != this.latitude)
            this.latitude = lat
    }

    fun updateLon(lon: String) {
        if(lon != this.longitude)
            this.longitude = lon
    }

    private fun isValidZipCode(zipCode: String) : Boolean {
        return zipCode.length == 5 && zipCode.all { it.isDigit() }
    }



    fun submitButtonClicked() = runBlocking{
        try {
            _currentConditions.value = service.getCurrentConditions(zipCode.toString())
        } catch(e : HttpException) {
            _showErrorDialog.value = true
        }
    }

    fun submitCurrentButtonClicked() = runBlocking{
        try {
            _currentConditions.value = service.getCurrentCurrentConditions(returnLat().toString(), returnLon().toString())
        } catch(e : HttpException) {
            _showErrorDialog.value = true
        }
    }

    fun returnZipCode() : String? {
        return zipCode
    }

    fun returnLat() : String? {
        return latitude
    }

    fun returnLon() : String? {
        return longitude
    }

    fun resetErrorDialog() {
        _showErrorDialog.value = false
    }
}