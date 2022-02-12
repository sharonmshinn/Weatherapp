package com.example.weatherapp


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MyAdapter(private val data: List<DayForecast>) : RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    @SuppressLint("NewApi")
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val dateView: TextView = view.findViewById(R.id.date)
        private val formatter = DateTimeFormatter.ofPattern("MMM dd")

        private val sunriseView: TextView = view.findViewById(R.id.sunrise_time)
        private val formatter2 = DateTimeFormatter.ofPattern("hh:mm a")

        private val sunsetView: TextView = view.findViewById(R.id.sunset_time)
        private val formatter3 = DateTimeFormatter.ofPattern("hh:mm a")

        private val tempView: TextView = view.findViewById(R.id.temp_day)

        private val lowView: TextView = view.findViewById(R.id.low_day)

        private val highView: TextView = view.findViewById(R.id.high_day)


        @SuppressLint("StringFormatMatches")
        fun bind(data: DayForecast) {
            val instant = Instant.ofEpochSecond(data.date)
            val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            dateView.text = formatter.format(dateTime)

            val instant2 = Instant.ofEpochSecond(data.sunrise)
            val sunriseTime = LocalDateTime.ofInstant(instant2, ZoneId.systemDefault())
            sunriseView.text = itemView.context.getString(R.string.sunrise, formatter2.format(sunriseTime))


            val instant3 = Instant.ofEpochSecond(data.sunset)
            val sunsetTime = LocalDateTime.ofInstant(instant3, ZoneId.systemDefault())
            sunsetView.text = itemView.context.getString(R.string.sunset, formatter3.format(sunriseTime))


            tempView.text = itemView.context.getString(R.string.temp_2, data.temp.day.toInt())

            lowView.text = itemView.context.getString(R.string.high_2, data.temp.max.toInt())

            highView.text = itemView.context.getString(R.string.low_2, data.temp.min.toInt())
        }


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_date, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])

    }

    override fun getItemCount() = data.size
}





