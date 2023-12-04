package com.portal.weatherapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portal.weatherapp.data.model.WeatherItem
import com.portal.weatherapp.domain.usecase.LocationDbUseCase
import com.portal.weatherapp.domain.usecase.WeatherUseCase
import com.portal.weatherapp.repository.db.location.LocationEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
    private val locationDbUseCase: LocationDbUseCase
) : ViewModel() {

    private val _cityResponse = MutableStateFlow<WeatherItem?>(null)
    val cityResponse = _cityResponse.asStateFlow()

    private val _cityValue = MutableStateFlow<String>("İstanbul")
    val cityValue = _cityValue.asStateFlow()

    private val _latValue = MutableStateFlow<Double>(0.00)
    val latValue = _latValue.asStateFlow()
    private val _lonValue = MutableStateFlow<Double>(0.00)
    val lonValue = _lonValue.asStateFlow()

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()

    fun getCityWeather(city: String) {
        viewModelScope.launch {
            try {
                _cityResponse.emit(weatherUseCase.getCityWeather(city))
            } catch (e: Exception) {
                _errorFlow.emit("An error occurred while fetching weather for the city.")
            }
        }
    }

    fun setLatAndLon(lat: Double, lon: Double) {
        _latValue.value = lat
        _lonValue.value = lon
    }

    fun updateLastCityValue(city: String){
        _cityValue.value = city
    }




}