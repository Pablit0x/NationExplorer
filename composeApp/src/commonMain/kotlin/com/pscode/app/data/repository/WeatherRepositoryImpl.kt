package com.pscode.app.data.repository


import com.pscode.app.data.model.weather.live.raw.toWeatherOverview
import com.pscode.app.domain.model.CurrentWeatherData
import com.pscode.app.domain.model.LocationData
import com.pscode.app.domain.model.MonthlyAverage
import com.pscode.app.domain.model.SixMonthsWeatherOverview
import com.pscode.app.domain.model.WeatherConditions
import com.pscode.app.domain.model.WeatherInfo
import com.pscode.app.domain.model.toWeatherConditions
import com.pscode.app.domain.model.toWeatherInfo
import com.pscode.app.domain.remote.WeatherApi
import com.pscode.app.domain.repository.WeatherRepository
import com.pscode.app.utils.Response
import kotlinx.datetime.LocalDate

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherByCity(cityName: String): Response<CurrentWeatherData> {
        return when (val result = weatherApi.getWeatherByCity(cityName = cityName)) {
            is Response.Success -> {
                Response.Success(data = result.data.toWeatherOverview())
            }

            is Response.Error -> {
                Response.Error(message = result.message)
            }
        }
    }

    override suspend fun getTemperatureRangePastSixMonths(locationData: LocationData): Response<SixMonthsWeatherOverview> {
        return when (val result =
            weatherApi.getTemperatureRangePastSixMonths(locationData = locationData)) {


            is Response.Success -> {
                val dailyData = result.data.daily
                val datesGroupedByMonths =
                    dailyData.time.map { LocalDate.parse(it) }.groupBy { it.month.name }

                val sixMonthsWeatherOverview =
                    SixMonthsWeatherOverview(monthAverages = datesGroupedByMonths.map { (month, datesInMonth) ->
                        val averageTemperature =
                            dailyData.temperature2mMax.zip(dailyData.temperature2mMin) { max, min -> (max + min) / 2 }
                                .filterIndexed { index, _ ->
                                    datesInMonth.contains(
                                        LocalDate.parse(
                                            dailyData.time[index]
                                        )
                                    )
                                }.average()
                        MonthlyAverage(month = month, averageValue = averageTemperature)
                    })
                Response.Success(data = sixMonthsWeatherOverview)
            }

            is Response.Error -> {
                Response.Error(message = result.message)
            }
        }
    }

    override suspend fun getWindSpeedRangePastSixMonths(locationData: LocationData): Response<SixMonthsWeatherOverview> {
        return when (val result =
            weatherApi.getWindSpeedRangePastSixMonths(locationData = locationData)) {


            is Response.Success -> {
                val dailyData = result.data.daily
                val datesGroupedByMonths =
                    dailyData.time.map { LocalDate.parse(it) }.groupBy { it.month.name }


                val sixMonthsWeatherOverview =
                    SixMonthsWeatherOverview(monthAverages = datesGroupedByMonths.map { (month, datesInMonth) ->
                        val averageWindSpeed = dailyData.windSpeed10mMax.filterIndexed { index, _ ->
                            datesInMonth.contains(
                                LocalDate.parse(
                                    dailyData.time[index]
                                )
                            )
                        }.average()

                        MonthlyAverage(month = month, averageValue = averageWindSpeed)
                    })
                Response.Success(data = sixMonthsWeatherOverview)
            }

            is Response.Error -> {
                Response.Error(message = result.message)
            }
        }
    }

    override suspend fun getDayLightRangePastSixMonths(locationData: LocationData): Response<SixMonthsWeatherOverview> {
        return when (val result =
            weatherApi.getDayLightDurationRangePastSixMonths(locationData = locationData)) {


            is Response.Success -> {
                val dailyData = result.data.daily
                val datesGroupedByMonths =
                    dailyData.time.map { LocalDate.parse(it) }.groupBy { it.month.name }


                val sixMonthsWeatherOverview =
                    SixMonthsWeatherOverview(monthAverages = datesGroupedByMonths.map { (month, datesInMonth) ->
                        val averageDayLightInSeconds =
                            dailyData.daylightDuration.filterIndexed { index, _ ->
                                datesInMonth.contains(
                                    LocalDate.parse(
                                        dailyData.time[index]
                                    )
                                )
                            }.average()

                        val averageDayLightInHours = ((averageDayLightInSeconds / 60) / 60)

                        MonthlyAverage(month = month, averageValue = averageDayLightInHours)
                    })
                Response.Success(data = sixMonthsWeatherOverview)
            }

            is Response.Error -> {
                Response.Error(message = result.message)
            }
        }
    }

    override suspend fun getRainSumRangePastSixMonths(locationData: LocationData): Response<SixMonthsWeatherOverview> {
        return when (val result =
            weatherApi.getRainSumRangePastSixMonths(locationData = locationData)) {


            is Response.Success -> {
                val dailyData = result.data.daily
                val datesGroupedByMonths =
                    dailyData.time.map { LocalDate.parse(it) }.groupBy { it.month.name }


                val sixMonthsWeatherOverview =
                    SixMonthsWeatherOverview(monthAverages = datesGroupedByMonths.map { (month, datesInMonth) ->
                        val averageRainSumInMm = dailyData.rainSum.filterIndexed { index, _ ->
                            datesInMonth.contains(
                                LocalDate.parse(
                                    dailyData.time[index]
                                )
                            )
                        }.average()

                        MonthlyAverage(month = month, averageValue = averageRainSumInMm)
                    })
                Response.Success(data = sixMonthsWeatherOverview)
            }

            is Response.Error -> {
                Response.Error(message = result.message)
            }
        }
    }

    override suspend fun getWeatherIcons(): Response<List<WeatherConditions>> {

        return when (val result = weatherApi.getLiveWeatherIcons()) {
            is Response.Success -> {
                val icons = result.data.icons.map {
                    it.toWeatherConditions()
                }
                Response.Success(data = icons)
            }

            is Response.Error -> {
                Response.Error(message = result.message)
            }
        }
    }

    override suspend fun getWeatherData(locationData: LocationData): Response<WeatherInfo> {
        when (val weatherIconsResult = weatherApi.getLiveWeatherIcons()) {
            is Response.Success -> {
                val icons = weatherIconsResult.data.icons.map {
                    it.toWeatherConditions()
                }
                val weatherResult =
                    weatherApi.getPrettyLiveWeatherByCity(locationData = locationData)
                return when (weatherResult) {

                    is Response.Success -> {
                        Response.Success(
                            data = weatherResult.data.toWeatherInfo(icons)
                        )
                    }

                    is Response.Error -> {
                        Response.Error(weatherResult.message)
                    }
                }
            }

            is Response.Error -> {
                return Response.Error(weatherIconsResult.message)
            }
        }
    }
}