package com.portal.weatherapp.data

import com.portal.weatherapp.domain.model.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val API_KEY = "8fbf87f4214666f2215f34efe9d3c8d1"
    }

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = API_KEY
    ): WeatherResponse

    @GET("weather")
    suspend fun getCityWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}