package com.portal.weatherapp.ui.home

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.portal.weatherapp.R
import com.portal.weatherapp.compose.BaseFragment
import com.portal.weatherapp.compose.viewBinding
import com.portal.weatherapp.databinding.FragmentHomeBinding
import com.portal.weatherapp.ui.home.adapter.WeatherAdapter
import com.portal.weatherapp.utilities.helper.Util
import com.portal.weatherapp.utilities.helper.Util.Companion.MAGIC_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var weatherAdapter: WeatherAdapter
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient




    override fun observeVariables() {
        lifecycleScope.launchWhenStarted {

            launch {
                homeViewModel.cityResponse.collect { weatherItem ->
                    weatherAdapter.updateItems(listOf(weatherItem?.main))
                    binding.apply {
                        tvCity.setText("${weatherItem?.name}, ${weatherItem?.sys?.country}")
                        weatherItem?.weather?.map {
                            tvDesc.setText(it.description)
                        }
                        tvSunriseValue.setText(weatherItem?.sys?.sunrise?.let {
                            convertUnixTimeToUTC(
                                it
                            )
                        })
                        tvSunsetValue.setText(weatherItem?.sys?.sunset?.let {
                            convertUnixTimeToUTC(
                                it
                            )
                        })
                        tvCelcius.setText(
                            getString(R.string.celcius).replace(
                                MAGIC_KEY, weatherItem
                                    ?.main?.temp.toString()
                            )
                        )
                        weatherItem?.weather?.map { mainItem ->
                            ivWeatherIcon.setImageResource(
                                Util.WeatherIcon.values()
                                    .find { util -> util.icon == mainItem.main }?.resource
                                    ?: R.drawable.ic_clouds
                            )
                        }

                    }

                }
            }
        }
    }

    override fun initUI(savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback {  }

        weatherAdapter = WeatherAdapter(requireContext())
        binding.rvWeather.adapter = weatherAdapter
        gridLayoutSize()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        val sharedPreferences =
            requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val cityName = sharedPreferences.getString("selectedCity", "İstanbul")
        cityName?.let { cityNameItem ->
            homeViewModel.getCityWeather(cityNameItem)
        }
           binding.llSearchNearby.setOnClickListener {
            fetchLocation()
        }
    }

    private fun gridLayoutSize() {
        val layoutManager = GridLayoutManager(context, 6)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    in 0..2 -> 2
                    3, 4 -> 3
                    else -> 3
                }
            }
        }
        binding.rvWeather.layoutManager = layoutManager
        binding.rvWeather.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT

    }

    private fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }
        task.addOnSuccessListener {
            if (it != null) {
                homeViewModel.getCoordResponse(it.latitude,it.longitude)
            }
        }
    }

    private fun convertUnixTimeToUTC(unixTime: Long): String {
        val turkeyFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        turkeyFormat.timeZone = TimeZone.getTimeZone("Europe/Istanbul")
        return turkeyFormat.format(Date(unixTime * 1000L))
    }
}
