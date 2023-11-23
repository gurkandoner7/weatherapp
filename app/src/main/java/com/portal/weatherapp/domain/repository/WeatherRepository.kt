package com.portal.weatherapp.domain.repository

import com.portal.weatherapp.data.model.WeatherItem

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherItem
    suspend fun getCityWeather(city: String): WeatherItem
}