package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SecondActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageId: Array<Int>


    private val adapterData = listOf<DayForecast>(
        DayForecast(1644191805,1644209902,1644268462, ForecastTemp(73f, 50f, 80f),1023f,100),
        DayForecast(1644278205,1644213502,1644268882, ForecastTemp(80f, 52f, 83f),1011f,99),
        DayForecast(1644364605,1644210082,1644265282,ForecastTemp(60f, 55f, 70f),1023f,80),
        DayForecast(1644451005,1644213502,1644266182,ForecastTemp(75f, 49f, 80f),1041f,76),
        DayForecast(1644537405,1644209902,1644271822,ForecastTemp(85f, 65f, 90f),1001f,50),
        DayForecast(1644623805,1644210082,1644271582,ForecastTemp(74f, 66f, 80f),1003f,100),
        DayForecast(1644710205,1644212842,1644265282,ForecastTemp(63f, 54f, 70f),1005f,97),
        DayForecast(1644796605,1644210082,1644268882,ForecastTemp(84f, 78f, 88f),1005f,46),
        DayForecast(1644883005,1644209242,1644266182,ForecastTemp(65f, 62f, 72f),1023f,97),
        DayForecast(1644969405,1644207262,1644271822,ForecastTemp(73f, 66f, 80f),1024f,23),
        DayForecast(1645055805,1644209902,1644268462,ForecastTemp(93f, 87f, 100f),1025f,64),
        DayForecast(1645142205,1644209242,1644271582,ForecastTemp(75f, 60f, 80f),1021f,13),
        DayForecast(1645228605,1644210082,1644266182,ForecastTemp(79f, 70f, 80f),1052f,54),
        DayForecast(1645315005,1644213502,1644265282,ForecastTemp(83f, 78f, 89f),1002f,75),
        DayForecast(1645401405,1644207262,1644271582,ForecastTemp(99f, 89f, 102f),1005f,99),
        DayForecast(1645487805,1644210082,1644268462,ForecastTemp(65f, 50f, 71f),1026f,23)
    )




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = MyAdapter(adapterData)

        imageId = arrayOf(
            R.drawable.sun,
            R.drawable.sun,
            R.drawable.sun,
            R.drawable.sun,
            R.drawable.sun,
            R.drawable.sun,
            R.drawable.sun,
            R.drawable.sun,
            R.drawable.sun,
            R.drawable.sun,
            R.drawable.sun,
            R.drawable.sun,
            R.drawable.sun,
            R.drawable.sun,
            R.drawable.sun,
            R.drawable.sun)



    }
}