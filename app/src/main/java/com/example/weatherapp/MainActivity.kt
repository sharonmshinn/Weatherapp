package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    private val apiKey = "5548515cd189dd95ffbb311a74da86ae"

    private lateinit var api: Api
    private lateinit var cityName: TextView
    private lateinit var currentTemp: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener{
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent);
        }
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/data/2.5/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        api = retrofit.create(Api::class.java)

        override fun onResume(){
            super.onResume()
            val call: Call<CurrentConditions> = api.getCurrentConditions("55426")
            call.enqueue(object : Callback<CurrentConditions>){
                override fun onResponse(
                    call: Call<CurrentConditions>
                    response: Reponse<CurrentConditions>
                ){
                    val currentConditions = response.body()
                    currentConditions?.let{
                        bindData(it)
                    }
                }
            }
            override fun onFailure(call: Call<CurrentConditions>, t: Throwable){

            }
        }



    }

}