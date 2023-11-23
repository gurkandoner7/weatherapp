package com.portal.weatherapp.domain.usecase

import com.portal.weatherapp.data.model.WeatherItem

interface WeatherUseCase {

    suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherItem
}