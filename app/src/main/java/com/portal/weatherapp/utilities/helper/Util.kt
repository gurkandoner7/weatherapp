package com.portal.weatherapp.utilities.helper

import com.portal.weatherapp.R

class Util {
    companion object {
        const val MAGIC_KEY = "%!%"
        const val GOOGLE_AUTH_KEY =
            "945721442055-biup34j8nrjml243kipsio9t0s17ulsc.apps.googleusercontent.com"
    }

    enum class WeatherIcon(val icon: String, val resource: Int) {
        CLEAR_SKY("Clear", R.drawable.ic_clear_sky),
        FEW_CLOUDS("Few Clouds", R.drawable.ic_few_clouds),
        SCATTERED_CLOUDS("Clouds", R.drawable.ic_scattered_clouds),
        BROKEN_CLOUDS("Broken Clouds", R.drawable.ic_broken_clouds),
        SHOWER_RAIN("Shower Rain", R.drawable.ic_rain),
        RAIN("Rain", R.drawable.ic_rain_sun),
        THUNDERSTORM("Thunderstorm", R.drawable.ic_thunderstorm),
        SNOW("Snow", R.drawable.ic_snow),
        FOG("Fog", R.drawable.ic_fog),
        MIST("Mist", R.drawable.ic_mist)
    }
}

