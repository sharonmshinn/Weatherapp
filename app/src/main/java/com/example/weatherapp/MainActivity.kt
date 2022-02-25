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
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    private val apiKey = "5548515cd189dd95ffbb311a74da86ae"

    private lateinit var api: Api
    private lateinit var cityName: TextView
    private lateinit var currentTemp: TextView
    private lateinit var conditionIcon: ImageView
    private lateinit var feelsLike: TextView
    private lateinit var lowTemp: TextView
    private lateinit var highTemp: TextView
    private lateinit var humidity: TextView
    private lateinit var pressure: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityName = findViewById(R.id.city_name)
        currentTemp = findViewById(R.id.temp)
        conditionIcon = findViewById(R.id.condition_icon)
        feelsLike = findViewById(R.id.feels_like)
        lowTemp = findViewById(R.id.low_temp)
        highTemp = findViewById(R.id.high_temp)
        humidity = findViewById(R.id.humidity)
        pressure = findViewById(R.id.pressure)

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            val intent = Intent(this, ForecastActivity::class.java)
            startActivity(intent);
        }

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        api = retrofit.create(Api::class.java)
    }

    override fun onResume(){
        super.onResume()
        val call: Call<CurrentConditions> = api.getCurrentConditions("55426")
        call.enqueue(object : Callback<CurrentConditions>{
            override fun onResponse(
                call: Call<CurrentConditions>,
                response: Response<CurrentConditions>
            ){
                val currentConditions = response.body()
                currentConditions?.let {
                    bindData(it)
                }
            }

            override fun onFailure(call: Call<CurrentConditions>, t: Throwable){
                t.printStackTrace()
            }
        })
    }

    private fun bindData(currentConditions: CurrentConditions){
        cityName.text = currentConditions.name
        currentTemp.text = getString(R.string.temp, currentConditions.main.temp.toInt())
        feelsLike.text = getString(R.string.feels_like, currentConditions.main.feelsLike.toInt())
        lowTemp.text = getString(R.string.low_temp, currentConditions.main.tempMin.toInt())
        highTemp.text = getString(R.string.high_temp, currentConditions.main.tempMax.toInt())
        humidity.text = getString(R.string.humidity, currentConditions.main.humidity.toInt()) + "%"
        pressure.text = getString(R.string.pressure, currentConditions.main.pressure.toInt())
        val iconName = currentConditions.weather.firstOrNull()?.icon
        val iconURL = "https://openweathermap.org/img/wn/${iconName}@2x.png"
        Glide.with(this)
            .load(iconURL)
            .into(conditionIcon)

    }


}