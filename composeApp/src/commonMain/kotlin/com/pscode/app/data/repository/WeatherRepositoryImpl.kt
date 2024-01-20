package com.pscode.app.data.repository


import com.pscode.app.data.model.weather.toWeatherOverview
import com.pscode.app.domain.model.LocationOverview
import com.pscode.app.domain.model.MonthlyAverage
import com.pscode.app.domain.model.SixMonthsWeatherOverview
import com.pscode.app.domain.model.WeatherOverview
import com.pscode.app.domain.remote.WeatherApi
import com.pscode.app.domain.repository.WeatherRepository
import com.pscode.app.utils.Response
import kotlinx.datetime.LocalDate

class WeatherRepositoryImpl(private val weatherApi: WeatherApi) : WeatherRepository {
    override suspend fun getWeatherByCity(cityName: String): Response<WeatherOverview> {
        return when (val result = weatherApi.getWeatherByCity(cityName = cityName)) {
            is Response.Success -> {
                Response.Success(data = result.data.toWeatherOverview())
            }

            is Response.Error -> {
                Response.Error(message = result.message)
            }
        }
    }

    override suspend fun getTemperatureRangePastSixMonths(locationOverview: LocationOverview): Response<SixMonthsWeatherOverview> {
        return when (val result =
            weatherApi.getTemperatureRangePastYear(locationOverview = locationOverview)) {


            is Response.Success -> {
                val dailyData = result.data.daily
                val datesGroupedByMonths =
                    dailyData.time.map { LocalDate.parse(it) }.groupBy { it.month.name }

                val sixMonthsWeatherOverview = SixMonthsWeatherOverview(
                    monthAverages = datesGroupedByMonths.map { (month, datesInMonth) ->
                        val averageTemperature =
                            dailyData.temperature2mMax.zip(dailyData.temperature2mMin) { max, min -> (max + min) / 2 }
                                .filterIndexed { index, _ ->
                                    datesInMonth.contains(
                                        LocalDate.parse(
                                            dailyData.time[index]
                                        )
                                    )
                                }
                                .average()
                        MonthlyAverage(month = month, averageTemperature = averageTemperature)
                    }
                )
                Response.Success(data = sixMonthsWeatherOverview)
            }

            is Response.Error -> {
                Response.Error(message = result.message)
            }
        }
    }
}