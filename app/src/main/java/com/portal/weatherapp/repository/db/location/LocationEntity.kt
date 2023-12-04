package com.portal.weatherapp.repository.db.location

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "location_table",
)
data class LocationEntity (
    @PrimaryKey
    val cityName: String,
    val lat: Double? = null,
    val lon: Double? = null
)
