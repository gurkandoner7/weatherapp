package com.portal.weatherapp.repository.db.location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao {

    @Query("SELECT * FROM location_table ORDER BY `cityName` DESC")
    fun getAllLocations(): List<LocationEntity>

    @Query("DELETE FROM location_table")
    fun deleteAllLocations()

    @Query("DELETE FROM location_table WHERE cityName = :cityName")
    fun deleteLocation(cityName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLocation(locationEntity: LocationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLocationList(locationEntities: List<LocationEntity>)

}