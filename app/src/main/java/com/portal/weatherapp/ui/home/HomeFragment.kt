package com.portal.weatherapp.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.portal.weatherapp.R
import com.portal.weatherapp.compose.BaseFragment
import com.portal.weatherapp.compose.viewBinding
import com.portal.weatherapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)

    override fun observeVariables() {
        lifecycleScope.launchWhenStarted {
            launch {
                homeViewModel.cityResponse.collect{
                    binding.tvLat.text = it.coord.lat.toString()
                    binding.tvLon.text = it.coord.lon.toString()
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
            btnCalculate.setOnClickListener {
            homeViewModel.getCityWeather(etAutoComplete.text.toString())
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
