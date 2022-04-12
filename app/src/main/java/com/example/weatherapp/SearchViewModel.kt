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

    //button not greyed out.
    //How to call api? How to get zip code string and call api
    private var zipCode: String? = null
    private val _zipCodeText = MutableLiveData<String>()
    private val _enableButton = MutableLiveData(false)
    private val _showErrorDialog = MutableLiveData(false)
    private val _currentConditions = MutableLiveData<CurrentConditions>()

    val currentConditions: LiveData<CurrentConditions>
        get() = _currentConditions

    val showErrorDialog: LiveData<Boolean>
        get() = _showErrorDialog

    val enableButton: LiveData<Boolean>
        get() = _enableButton

    val zipCodeText: LiveData<String>
        get() = _zipCodeText

    fun updateZipCode(zipCode: String) {
        if(zipCode != this.zipCode)
            this.zipCode = zipCode
            _enableButton.value = isValidZipCode(zipCode)
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

    fun returnZipCode() : String? {
        return zipCode
    }

    fun resetErrorDialog() {
        _showErrorDialog.value = false
    }
}