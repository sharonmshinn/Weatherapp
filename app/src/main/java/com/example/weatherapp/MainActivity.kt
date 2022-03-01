package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.ActivityMainBinding
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(this, ForecastActivity::class.java)
            startActivity(intent);
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.currentConditions.observe(this) {currentConditions ->
            bindData(currentConditions)
        }
        viewModel.loadData()
    }

    private fun bindData(currentConditions: CurrentConditions){
        binding.cityName.text = currentConditions.name
        binding.temp.text = getString(R.string.temp, currentConditions.main.temp.toInt())
        binding.feelsLike.text = getString(R.string.feels_like, currentConditions.main.feelsLike.toInt())
        binding.lowTemp.text = getString(R.string.low_temp, currentConditions.main.tempMin.toInt())
        binding.highTemp.text = getString(R.string.high_temp, currentConditions.main.tempMax.toInt())
        binding.humidity.text = getString(R.string.humidity, currentConditions.main.humidity.toInt()) + "%"
        binding.pressure.text = getString(R.string.pressure, currentConditions.main.pressure.toInt())
        val iconName = currentConditions.weather.firstOrNull()?.icon
        val iconURL = "https://openweathermap.org/img/wn/${iconName}@2x.png"
        Glide.with(this)
            .load(iconURL)
            .into(binding.conditionIcon)

    }


}