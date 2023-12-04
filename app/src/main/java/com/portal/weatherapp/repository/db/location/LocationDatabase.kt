package com.portal.weatherapp.repository.db.location

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [LocationEntity::class],
    version = 1,
    exportSchema = false
)

abstract class LocationDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}