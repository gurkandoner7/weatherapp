package com.portal.weatherapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portal.weatherapp.data.model.WeatherItem
import com.portal.weatherapp.domain.usecase.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val weatherUseCase: WeatherUseCase) : ViewModel() {

    private val _weatherResponse = MutableSharedFlow<WeatherItem>()
    val weatherResponse = _weatherResponse.asSharedFlow()
    private val _latValue = MutableStateFlow<Double>(0.00)
    val latValue = _latValue.asStateFlow()
    private val _lonValue = MutableStateFlow<Double>(0.00)
    val lonValue = _lonValue.asStateFlow()


    fun getCurrentWeather() {
        viewModelScope.launch {
            weatherUseCase.getCurrentWeather(_latValue.value, _lonValue.value)
        }
    }

    fun setLatAndLon(lat: Double, lon: Double) {
        _latValue.value = lat
        _lonValue.value = lon
    }
}