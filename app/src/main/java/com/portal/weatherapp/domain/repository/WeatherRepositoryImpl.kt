package com.portal.weatherapp.domain.repository

import com.portal.weatherapp.data.WeatherService
import com.portal.weatherapp.data.model.WeatherItem
import com.portal.weatherapp.domain.mapper.WeatherDataMapper
import com.portal.weatherapp.domain.mapper.toWeatherItem
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val dataMapper: WeatherDataMapper
) : WeatherRepository {
    override suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherItem {
        try {
            return weatherService.getCurrentWeather(lat, lon).toWeatherItem()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCityWeather(city: String): WeatherItem {
        try {
            return weatherService.getCityWeather(city).toWeatherItem()
        } catch (e: Exception) {
            throw e
        }
    }
}

