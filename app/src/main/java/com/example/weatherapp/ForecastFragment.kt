package com.example.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ActivitySecondBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForecastFragment : Fragment() {

    private val args: ForecastFragmentArgs by navArgs()
    @Inject
    lateinit var viewModel: ForecastViewModel

    private lateinit var binding: ActivitySecondBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivitySecondBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Forecast"

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()
        viewModel.forecast.observe(this) { forecast ->
            binding.recyclerView.adapter = MyAdapter(forecast.list)
        }
        //Is this correct?
        if(args.zip != null) {
            viewModel.loadData(args.zip)
        } else {
            viewModel.loadLocationData(args.latArgs, args.longArgs)
        }

    }
}