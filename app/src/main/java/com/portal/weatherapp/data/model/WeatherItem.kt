package com.portal.weatherapp.data.model

data class WeatherItem(
    val coord: CoordItem,
    val weather: List<WeatherListItem>,
    val base: String,
    val main: MainItem,
    val visibility: Int,
    val wind: WindItem,
    val clouds: CloudsItem,
    val dt: Long,
    val sys: SysItem,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)

data class CoordItem(
    val lon: Double,
    val lat: Double
)

data class WeatherListItem(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class MainItem(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val sea_level: Int,
    val grnd_level: Int,
    val humidity: Int
)

data class WindItem(
    val speed: Double,
    val deg: Int
)

data class CloudsItem(
    val all: Int
)

data class SysItem(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)


data class AdapterItemList(
    val header: String,
    val desc: String,
    val icon: Int,
)

data class WeatherData(
    val header: String,
    val icon: Int,
    val desc: String
)