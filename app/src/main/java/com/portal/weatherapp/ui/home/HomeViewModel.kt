package com.portal.weatherapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portal.weatherapp.data.model.WeatherItem
import com.portal.weatherapp.domain.usecase.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val weatherUseCase: WeatherUseCase) : ViewModel() {

    private val _weatherResponse = MutableSharedFlow<WeatherItem>()
    val weatherResponse = _weatherResponse.asSharedFlow()

    private val _cityResponse = MutableSharedFlow<WeatherItem>()
    val cityResponse = _cityResponse.asSharedFlow()

    private val _latValue = MutableStateFlow<Double>(0.00)
    val latValue = _latValue.asStateFlow()
    private val _lonValue = MutableStateFlow<Double>(0.00)
    val lonValue = _lonValue.asStateFlow()

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()


    fun getCurrentWeather() {
        viewModelScope.launch {
            try {
                val weatherItem = weatherUseCase.getCurrentWeather(_latValue.value, _lonValue.value)
                _weatherResponse.emit(weatherItem)
            } catch (e: Exception) {
                _errorFlow.emit("An error occurred while fetching current weather.")
            }
        }
    }

    fun getCityWeather(city: String) {
        viewModelScope.launch {
            try {
                val weatherItem = weatherUseCase.getCityWeather(city)
                _cityResponse.emit(weatherItem)
            } catch (e: Exception) {
                _errorFlow.emit("An error occurred while fetching weather for the city.")
            }
        }
    }

    fun setLatAndLon(lat: Double, lon: Double) {
        _latValue.value = lat
        _lonValue.value = lon
    }




}