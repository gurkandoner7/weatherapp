package com.portal.weatherapp.domain.mapper

import com.portal.weatherapp.data.model.*
import com.portal.weatherapp.domain.model.response.*
import javax.inject.Inject

class WeatherDataMapper @Inject constructor()
    fun WeatherResponse.toWeatherItem(): WeatherItem {
        return WeatherItem(
            coord = coord.toCoordItem(),
            weather = weather.map { it.toWeatherListItem() },
            base = base,
            main = main.toMainItem(),
            visibility = visibility,
            wind = wind.toWindItem(),
            clouds = clouds.toCloudsItem(),
            dt = dt,
            sys = sys.toSysItem(),
            timezone = timezone,
            id = id,
            name = name,
            cod = cod
        )
    }

    private fun CoordResponseItem.toCoordItem(): CoordItem {
        return CoordItem(
            lon = lon,
            lat = lat
        )
    }

    private fun WeatherResponseItem.toWeatherListItem(): WeatherListItem {
        return WeatherListItem(
            id = id,
            main = main,
            description = description,
            icon = icon
        )
    }

    private fun MainResponseItem.toMainItem(): MainItem {
        return MainItem(
            temp = temp,
            feels_like = feels_like,
            temp_min = temp_min,
            temp_max = temp_max,
            pressure = pressure,
            humidity = humidity
        )
    }

    private fun WindResponseItem.toWindItem(): WindItem {
        return WindItem(
            speed = speed,
            deg = deg
        )
    }

    private fun CloudsResponseItem.toCloudsItem(): CloudsItem {
        return CloudsItem(
            all = all
        )
    }

    private fun SysResponseItem.toSysItem(): SysItem {
        return SysItem(
            type = type,
            id = id,
            country = country,
            sunrise = sunrise,
            sunset = sunset
        )
    }
