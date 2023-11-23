package com.portal.weatherapp.domain.model.response

import java.io.Serializable

data class WeatherResponse(
    val coord: CoordResponseItem,
    val weather: List<WeatherResponseItem>,
    val base: String,
    val main: MainResponseItem,
    val visibility: Int,
    val wind: WindResponseItem,
    val clouds: CloudsResponseItem,
    val dt: Long,
    val sys: SysResponseItem,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
) : Serializable

data class CoordResponseItem(
    val lon: Double,
    val lat: Double
) : Serializable

data class WeatherResponseItem(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
) : Serializable

data class MainResponseItem(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
) : Serializable

data class WindResponseItem(
    val speed: Double,
    val deg: Int
) : Serializable

data class CloudsResponseItem(
    val all: Int
) : Serializable

data class SysResponseItem(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Long,
    val sunset: Long
) : Serializable