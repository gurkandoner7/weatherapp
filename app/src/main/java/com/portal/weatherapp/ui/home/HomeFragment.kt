package com.portal.weatherapp.ui.home

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.portal.weatherapp.R
import com.portal.weatherapp.compose.BaseFragment
import com.portal.weatherapp.compose.viewBinding
import com.portal.weatherapp.databinding.FragmentHomeBinding
import com.portal.weatherapp.ui.home.adapter.WeatherAdapter
import com.portal.weatherapp.utilities.helper.Util.Companion.MAGIC_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var weatherAdapter: WeatherAdapter


    override fun observeVariables() {
        lifecycleScope.launchWhenStarted {
            launch {
                homeViewModel.cityResponse.collect { weatherItem ->
                    weatherAdapter.updateItems(listOf(weatherItem.main))
                    binding.apply {
                        tvCity.setText("${weatherItem.name}, Turkey")
                       weatherItem.weather.map {
                            tvDesc.setText(it.description)
                        }
                           tvSunriseValue.setText(convertUnixTimeToUTC(weatherItem.sys.sunrise))
                           tvSunsetValue.setText(convertUnixTimeToUTC(weatherItem.sys.sunset))
                           tvCelcius.setText(getString(R.string.celcius).replace(MAGIC_KEY, weatherItem.main.temp.toString()) )
                    }

                }
            }
        }
    }

    override fun initUI(savedInstanceState: Bundle?) {
        weatherAdapter = WeatherAdapter(requireContext())
        binding.rvWeather.adapter = weatherAdapter
        gridLayoutSize()
        /*
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

                }
        */
        homeViewModel.getCityWeather("Adana")


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


    private fun convertUnixTimeToUTC(unixTime: Long): String {
        val turkeyFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        turkeyFormat.timeZone = TimeZone.getTimeZone("Europe/Istanbul")
        return turkeyFormat.format( Date(unixTime * 1000L))
    }}
