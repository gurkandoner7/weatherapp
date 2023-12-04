package com.portal.weatherapp.ui.location

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
class LocationViewModel @Inject constructor(
    private val locationDbUseCase: LocationDbUseCase,
    private val weatherUseCase: WeatherUseCase
) :
    ViewModel() {

    private val _getAllLocations = MutableStateFlow<List<LocationEntity>>(emptyList())
    val getAllLocations = _getAllLocations.asStateFlow()

    private val _weatherResponse = MutableSharedFlow<WeatherItem>()
    val weatherResponse = _weatherResponse.asSharedFlow()

    private val _latValue = MutableStateFlow<Double>(0.00)
    val latValue = _latValue.asStateFlow()

    private val _lonValue = MutableStateFlow<Double>(0.00)
    val lonValue = _lonValue.asStateFlow()

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()

    fun getCoord(cityName: String) {
        viewModelScope.launch {
            try {
                val weatherItem = weatherUseCase.getCityWeather(cityName)
                _weatherResponse.emit(weatherItem)
            } catch (e: Exception) {
                _errorFlow.emit("An error occurred while fetching current weather.")
            }
        }

    }

    fun setLatAndLon(lat: Double, lon: Double) {
        _latValue.value = lat
        _lonValue.value = lon
    }

    fun getLocations() {
        viewModelScope.launch(Dispatchers.IO) {
            val locations = locationDbUseCase.getAllLocations()
            _getAllLocations.emit(locations)
        }
    }

    fun addLocation(cityName: String, lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            locationDbUseCase.addLocation(LocationEntity(cityName, lat, lon))
            getLocations()
        }
    }

    fun deleteAllLocations() {
        viewModelScope.launch {
            locationDbUseCase.deleteAllLocations()
        }
    }

    fun deleteLocation(cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            locationDbUseCase.deleteItem(cityName)
        }
    }
}