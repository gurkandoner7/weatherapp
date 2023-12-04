package com.portal.weatherapp.repository.db.location

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject internal constructor(
    locationDatabase: LocationDatabase
) {
    private val locationDao = locationDatabase.locationDao()

    fun getAllLocations(): List<LocationEntity> = locationDao.getAllLocations()

    fun addLocation(locationEntity: LocationEntity) = locationDao.addLocation(locationEntity)

    fun deleteLocation(cityName: String) = locationDao.deleteLocation(cityName)

    fun deleteAllLocations() = locationDao.deleteAllLocations()

    fun addLocationList(locationEntity: List<LocationEntity>) =
        locationDao.addLocationList(locationEntity)


}