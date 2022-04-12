package com.example.weatherapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgument
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.databinding.FragmentCurrentConditionsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CurrentConditionsFragment : Fragment() {

    private val args: CurrentConditionsFragmentArgs by navArgs()
    private lateinit var binding: FragmentCurrentConditionsBinding
    @Inject
    lateinit var viewModel: CurrentConditionsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentConditionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCurrentConditionsBinding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.title = "Current Conditions"

        binding.button.setOnClickListener {
            val zip = CurrentConditionsFragmentDirections.actionCurrentConditionsFragmentToForecastFragment(args.zipArgs)
            Navigation.findNavController(it).navigate(zip)

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.currentConditions.observe(this) {currentConditions ->
            bindData(currentConditions)
        }
        viewModel.loadData(args.currentArgs!!)
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