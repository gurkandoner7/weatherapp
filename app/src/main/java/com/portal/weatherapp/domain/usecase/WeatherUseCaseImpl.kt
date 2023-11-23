package com.portal.weatherapp.domain.usecase

import com.portal.weatherapp.data.model.WeatherItem
import com.portal.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherUseCaseImpl @Inject constructor(private val weatherRepository: WeatherRepository) :
    WeatherUseCase {
    override suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherItem =
        weatherRepository.getCurrentWeather(lat, lon)
}