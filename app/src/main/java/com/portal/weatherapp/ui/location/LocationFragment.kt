package com.portal.weatherapp.ui.location

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.portal.weatherapp.R
import com.portal.weatherapp.compose.BaseFragment
import com.portal.weatherapp.compose.viewBinding
import com.portal.weatherapp.databinding.FragmentLocationBinding
import com.portal.weatherapp.ui.location.adapter.LocationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.util.Locale

@AndroidEntryPoint
class LocationFragment : BaseFragment(R.layout.fragment_location) {

    private val locationViewModel: LocationViewModel by activityViewModels()

    private val binding: FragmentLocationBinding by viewBinding(FragmentLocationBinding::bind)

    private lateinit var adapter: LocationAdapter

    override fun observeVariables() {
        lifecycleScope.launchWhenStarted {
            launch {
                locationViewModel.weatherResponse.collect {
                    locationViewModel.setLatAndLon(lat = it.coord.lat, lon = it.coord.lon)
                    locationViewModel.apply {
                        addLocation(
                            it.name, latValue.value, lonValue.value
                        )
                    }

                }
            }
            launch {
                locationViewModel.getAllLocations.collect { locations ->
                    locations.forEach {
                        Log.d("LocationFragment", it.cityName)
                    }
                    adapter.updateItems(locations)
                }
            }
        }
    }

    override fun initUI(savedInstanceState: Bundle?) {

        adapter = LocationAdapter(requireContext(), onItemDeleteClicked = { locationItem ->
            locationViewModel.deleteLocation(locationItem.cityName)

        }, onItemClicked = { locationItem ->
            val sharedPreferences =
                requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().putString("selectedCity", locationItem.cityName).apply()

            findNavController().navigate(
                R.id.action_navigation_location_to_navigation_home,
            )
        })
        binding.rvLocation.adapter = adapter
        adapter.updateItems(locationViewModel.getAllLocations.value)
        locationViewModel.getLocations()

        binding.apply {
            val searchIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_search)
            val clearIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_clear_text)
            etAutoComplete.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val text = s.toString().trim()
                    if (text.isNotEmpty()) {
                        etAutoComplete.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, clearIcon, null
                        )
                    } else {
                        etAutoComplete.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, searchIcon, null
                        )
                    }

                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        getFilteredCityList(s.toString().toLowerCase(Locale.getDefault()))
                    )
                    etAutoComplete.setAdapter(adapter)
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
            binding.btnDeleteText.setOnClickListener {
                etAutoComplete.text.clear()
            }
            binding.btnAdd.setOnClickListener {
                val location = etAutoComplete.text.toString()
                val filteredCityList =
                    getFilteredCityList(location.toLowerCase(Locale.getDefault()))

                if (filteredCityList.contains(location)) {
                    locationViewModel.getCoord(location)


                    etAutoComplete.text.clear()
                } else {
                    Toast.makeText(requireContext(), "Location Not Found", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }


    }

    private fun getFilteredCityList(searchText: String): List<String> {
        val filteredCityList = mutableListOf<String>()

        try {
            val inputStream = resources.openRawResource(R.raw.tr)
            val jsonContent = inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonContent)

            for (i in 0 until jsonArray.length()) {
                val cityName = jsonArray.getJSONObject(i).getString("city")
                if (cityName.toLowerCase(Locale.getDefault()).contains(searchText)) {
                    filteredCityList.add(cityName)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return filteredCityList
    }


}