package com.example.weatherapp

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(), ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var binding: FragmentSearchBinding
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private val REQUEST_LOCATION_PERMISSION = 300
    @Inject
    lateinit var viewModel: SearchViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Search"

        binding = FragmentSearchBinding.bind(view)


        viewModel.enableButton.observe(viewLifecycleOwner) { enable ->
            binding.searchButton.isEnabled = enable
        }

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(activity as AppCompatActivity)

        locationRequest = LocationRequest.create()
        locationRequest.interval = 0L
        locationRequest.fastestInterval = 0L
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    viewModel.updateLat(location.latitude.toString())
                    viewModel.updateLon(location.longitude.toString())
                }
            }
        }

        viewModel.showErrorDialog.observe(viewLifecycleOwner) { showError ->
            if (showError) {
                ErrorDialogFragment().show(childFragmentManager, ErrorDialogFragment.TAG)
            }
        }

        binding.zipCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.toString()?.let { viewModel.updateZipCode(it) }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })


        binding.searchButton.setOnClickListener() {
            viewModel.submitButtonClicked()
            if (!(viewModel.showErrorDialog.value!!)) {
                val currentConditions =
                    SearchFragmentDirections.actionSearchFragmentToCurrentConditionsFragment(
                        viewModel.returnZipCode(),
                        viewModel.currentConditions.value,
                        null,
                        null
                    )
                Navigation.findNavController(it).navigate(currentConditions)
            } else {
                viewModel.resetErrorDialog()
            }
        }


        binding.locationButton.setOnClickListener() {
            getLastLocation()

            if (ContextCompat.checkSelfPermission(
                    (activity as AppCompatActivity), Manifest.permission.ACCESS_COARSE_LOCATION
                ) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                viewModel.submitCurrentButtonClicked()
                if (!(viewModel.showErrorDialog.value!!)) {
                    val currentConditions =
                        SearchFragmentDirections.actionSearchFragmentToCurrentConditionsFragment(
                            null,
                            viewModel.currentConditions.value,
                            viewModel.returnLat(),
                            viewModel.returnLon()
                        )
                    Navigation.findNavController(it).navigate(currentConditions)
                } else {
                    viewModel.resetErrorDialog()
                }
            } else {
                requestLocationPermission()
            }
        }
    }

    private fun getLastLocation() {
        val locationRequest = LocationRequest.create()
        locationRequest.interval = 0L
        locationRequest.fastestInterval = 0L
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        if(ContextCompat.checkSelfPermission(
                (activity as AppCompatActivity),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper()!!)

            fusedLocationProviderClient.lastLocation.addOnSuccessListener((activity as AppCompatActivity)) { task ->
                val location : Location? = task

                if (location != null) {
                    viewModel.updateLat(location.latitude.toString())
                    viewModel.updateLon(location.longitude.toString())
                } else {
                    // location is null!
                }
            }
        }
    }

/*
    private fun startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.getMainLooper()
        )
    }
 */

    private fun requestLocationPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale
                (activity as AppCompatActivity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            showLocationPermissionRationale()
        } else {
            ActivityCompat.requestPermissions(
                activity as AppCompatActivity,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    private fun showLocationPermissionRationale() {
        AlertDialog.Builder(activity as AppCompatActivity)
            .setMessage(R.string.location_permission_rationale)
            .setNeutralButton(R.string.ok) { _, _ ->
                ActivityCompat.requestPermissions(
                    activity as AppCompatActivity,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    REQUEST_LOCATION_PERMISSION
                )
            }
            .create()
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }




    /*
    private fun getLocation() {
        val task = fusedLocationProviderClient.lastLocation
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        task.addOnSuccessListener {
            if(it != null){
                Toast.makeText(applicationContext, "${it.latitude} ${it.longitude}", Toast.LENGTH_SHORT).show()
            }
        }
    }


     */


}