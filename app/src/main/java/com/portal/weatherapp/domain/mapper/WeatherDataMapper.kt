package com.portal.weatherapp.domain.mapper

import com.portal.weatherapp.R
import com.portal.weatherapp.data.model.CloudsItem
import com.portal.weatherapp.data.model.CoordItem
import com.portal.weatherapp.data.model.MainItem
import com.portal.weatherapp.data.model.SysItem
import com.portal.weatherapp.data.model.WeatherData
import com.portal.weatherapp.data.model.WeatherItem
import com.portal.weatherapp.data.model.WeatherListItem
import com.portal.weatherapp.data.model.WindItem
import com.portal.weatherapp.domain.model.response.CloudsResponseItem
import com.portal.weatherapp.domain.model.response.CoordResponseItem
import com.portal.weatherapp.domain.model.response.MainResponseItem
import com.portal.weatherapp.domain.model.response.SysResponseItem
import com.portal.weatherapp.domain.model.response.WeatherResponse
import com.portal.weatherapp.domain.model.response.WeatherResponseItem
import com.portal.weatherapp.domain.model.response.WindResponseItem
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
        humidity = humidity,
        sea_level = sea_level,
        grnd_level = grnd_level
    )
}

fun MainItem.toWeatherData(): List<WeatherData> {
    val weatherDataList = mutableListOf<WeatherData>()

    listOfNotNull(
        feels_like.let {
            weatherDataList.add(WeatherData("Feels Like", R.drawable.ic_feelslike, "$it°C"))
        },
        temp_max.let {
            weatherDataList.add(WeatherData("Max Temp", R.drawable.ic_temp, "$it°C"))
        },
        temp_min.let {
            weatherDataList.add(WeatherData("Min Temp", R.drawable.ic_temp, "$it°C"))
        },
        humidity.let {
            weatherDataList.add(WeatherData("Humidity", R.drawable.ic_humidity, "$it%"))
        },
        pressure.let {
            weatherDataList.add(WeatherData("Pressure", R.drawable.ic_barometer, "$it hPa"))
        },
        sea_level.let {
            weatherDataList.add(WeatherData("Sea Level", R.drawable.ic_sea_level, "$it m"))
        },
        grnd_level.let {
            weatherDataList.add(WeatherData("Ground Level", R.drawable.ic_ground, "$it m"))
        },


    )

    return weatherDataList
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
