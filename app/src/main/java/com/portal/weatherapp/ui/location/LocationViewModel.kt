package com.portal.weatherapp.ui.location

import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.portal.weatherapp.domain.usecase.LocationDbUseCase
import com.portal.weatherapp.repository.db.location.LocationEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val locationDbUseCase: LocationDbUseCase) :
    ViewModel() {

    private val _getAllLocations = MutableSharedFlow<List<LocationEntity>>()
    val getAllLocations = _getAllLocations.asSharedFlow()

    fun getLocations() {
        viewModelScope.launch(Dispatchers.IO) {
            val locations = locationDbUseCase.getAllLocations()
            _getAllLocations.emit(locations)
        }
    }

    fun addLocation(cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            locationDbUseCase.addLocation(LocationEntity(cityName))
        }
    }

    fun deleteAllLocations() {
        viewModelScope.launch(Dispatchers.IO) {
            locationDbUseCase.deleteAllLocations()
        }
    }
}