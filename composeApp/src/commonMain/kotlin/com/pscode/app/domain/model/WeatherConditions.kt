package com.pscode.app.domain.model

import com.pscode.app.data.model.weather.live.icons.Icon

data class WeatherConditions(
    val name: String, val image: String, val weatherDesc: String
)

fun Icon.toWeatherConditions(): WeatherConditions {
    return WeatherConditions(
        name = this.name, image = this.image, weatherDesc = this.description
    )
}
