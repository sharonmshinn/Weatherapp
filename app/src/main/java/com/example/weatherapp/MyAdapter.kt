package com.example.weatherapp


import android.annotation.SuppressLint
import android.inputmethodservice.Keyboard
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.databinding.ActivitySecondBinding
import com.example.weatherapp.databinding.RowDateBinding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MyAdapter(private val data: List<DayForecast>) : RecyclerView.Adapter<MyAdapter.ViewHolder>(){


    @SuppressLint("NewApi")
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RowDateBinding.bind(view)
        private val formatter = DateTimeFormatter.ofPattern("MMM dd")
        private val formatter2 = DateTimeFormatter.ofPattern("hh:mm a")



        @SuppressLint("StringFormatMatches")
        fun bind(data: DayForecast) {
            val instant = Instant.ofEpochSecond(data.dt)
            val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            binding.date.text = formatter.format(dateTime)

            val instant2 = Instant.ofEpochSecond(data.sunrise)
            val sunriseTime = LocalDateTime.ofInstant(instant2, ZoneId.systemDefault())
            binding.sunriseTime.text = itemView.context.getString(R.string.sunrise, formatter2.format(sunriseTime))


            val instant3 = Instant.ofEpochSecond(data.sunset)
            val sunsetTime = LocalDateTime.ofInstant(instant3, ZoneId.systemDefault())
            binding.sunsetTime.text = itemView.context.getString(R.string.sunset, formatter2.format(sunsetTime))


            binding.tempDay.text = itemView.context.getString(R.string.temp_2, data.temp.day.toInt())

            binding.lowDay.text = itemView.context.getString(R.string.high_2, data.temp.max.toInt())

            binding.highDay.text = itemView.context.getString(R.string.low_2, data.temp.min.toInt())


            val iconName = data.weather.firstOrNull()?.icon
            val iconURL = "https://openweathermap.org/img/wn/${iconName}@2x.png"
            Glide.with(binding.weatherIcon)
                .load(iconURL)
                .into(binding.weatherIcon)


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





