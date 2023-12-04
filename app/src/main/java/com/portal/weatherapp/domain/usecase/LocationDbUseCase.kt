package com.portal.weatherapp.domain.usecase

import com.portal.weatherapp.repository.db.location.LocationEntity

interface LocationDbUseCase {
    suspend fun getAllLocations(): List<LocationEntity>

    suspend fun addLocation(locationEntity: LocationEntity)

    suspend fun addLocationList(locationEntity: List<LocationEntity>)

    suspend fun deleteAllLocations()

    suspend fun deleteItem(cityName: String)
}