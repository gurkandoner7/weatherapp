package com.portal.weatherapp.ui.location

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.portal.weatherapp.R
import com.portal.weatherapp.compose.BaseFragment
import com.portal.weatherapp.compose.viewBinding
import com.portal.weatherapp.databinding.FragmentLocationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.util.Locale

@AndroidEntryPoint
class LocationFragment : BaseFragment(R.layout.fragment_location) {

    private val locationViewModel: LocationViewModel by activityViewModels()

    private val binding: FragmentLocationBinding by viewBinding(FragmentLocationBinding::bind)


    override fun observeVariables() {
        lifecycleScope.launchWhenStarted {
            launch {
                locationViewModel.getAllLocations.collect { locations ->
                    for (location in locations) {
                        Log.d("LocationFragment", "City Name: ${location.cityName}")
                    }
                }
            }
        }
    }

    override fun initUI(savedInstanceState: Bundle?) {


        binding.apply {
            etAutoComplete.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
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

            btnAdd.setOnClickListener {
                locationViewModel.addLocation(etAutoComplete.text.toString())
            }

            btnDelete.setOnClickListener {
                locationViewModel.deleteAllLocations()
            }

            btnGetLocations.setOnClickListener {
                locationViewModel.getLocations()
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