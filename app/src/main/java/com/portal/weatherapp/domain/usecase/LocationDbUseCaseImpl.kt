package com.portal.weatherapp.domain.usecase

import com.portal.weatherapp.repository.db.location.LocationEntity
import com.portal.weatherapp.repository.db.location.LocationRepository
import javax.inject.Inject

class LocationDbUseCaseImpl @Inject constructor(private val locationRepository: LocationRepository) : LocationDbUseCase {
    override suspend fun getAllLocations(): List<LocationEntity> = locationRepository.getAllLocations()

    override suspend fun addLocation(locationEntity: LocationEntity) = locationRepository.addLocation(locationEntity)

    override suspend fun deleteAllLocations() = locationRepository.deleteAllLocations()

    override suspend fun addLocationList(locationEntity: List<LocationEntity>) = locationRepository.addLocationList(locationEntity)

}