package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.carlosgub.kotlinm.charts.ChartAnimation
import com.carlosgub.kotlinm.charts.bar.BarChart
import com.carlosgub.kotlinm.charts.bar.BarChartCategory
import com.carlosgub.kotlinm.charts.bar.BarChartConfig
import com.carlosgub.kotlinm.charts.bar.BarChartData
import com.carlosgub.kotlinm.charts.bar.BarChartEntry
import com.carlosgub.kotlinm.charts.round
import com.pscode.app.SharedRes
import com.pscode.app.domain.model.SixMonthsWeatherOverview
import com.pscode.app.presentation.screens.countries.overview.SelectableItemWithIcon

@Composable
fun AveragesBarChart(
    sixMonthsWeatherOverview: List<SixMonthsWeatherOverview>,
    chartSelectionItems: List<SelectableItemWithIcon>,
    onChartSelectionItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val currentlySelectedItem = chartSelectionItems.first { it.isSelected }.label
    var selectedValue by remember { mutableStateOf<String?>(null) }

    Column(modifier = modifier) {

        when (currentlySelectedItem) {
            "Temperature" -> {
                if (sixMonthsWeatherOverview[0].monthAverages.isNotEmpty()) {
                    val barChartData =
                        BarChartData(categories = sixMonthsWeatherOverview[0].monthAverages.flatMap { monthlyAverage ->
                            listOf(
                                BarChartCategory(
                                    name = monthlyAverage.month.take(3), entries = listOf(
                                        BarChartEntry(
                                            x = monthlyAverage.month,
                                            y = monthlyAverage.averageValue.toFloat(),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                )
                            )
                        })


                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = selectedValue ?: "",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(
                                alpha = if (selectedValue == null) 0f else 1f
                            )
                        )
                    }

                    BarChart(
                        data = barChartData,
                        config = BarChartConfig(
                            thickness = 36.dp,
                        ),
                        yAxisLabel = {

                            val temperature = it.toString().padStart(5)

                            Text(
                                text = "$temperature °C",
                                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                textAlign = TextAlign.Start,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        xAxisLabel = {
                            val xLabel = (it as String).take(3)
                            Text(
                                text = xLabel,
                                fontFamily = FontFamily.Monospace,
                                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                modifier = Modifier.padding(vertical = 4.dp),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        overlayDataEntryLabel = { month, value ->
                            val temperature = value as Float
                            selectedValue = "${
                                SharedRes.string.average_temperature.format(
                                    month = month.lowercase().capitalize(locale = Locale.current)
                                )
                            }: ${temperature.round(2)}℃"
                        },
                        modifier = Modifier.fillMaxSize().weight(1f),
                        animation = ChartAnimation.Sequenced(),
                    )
                }
            }

            "Wind Speed" -> {
                if (sixMonthsWeatherOverview[1].monthAverages.isNotEmpty()) {
                    val barChartData =
                        BarChartData(categories = sixMonthsWeatherOverview[1].monthAverages.flatMap { monthlyAverage ->
                            listOf(
                                BarChartCategory(
                                    name = monthlyAverage.month.take(3), entries = listOf(
                                        BarChartEntry(
                                            x = monthlyAverage.month,
                                            y = monthlyAverage.averageValue.toFloat(),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                )
                            )
                        })


                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = selectedValue ?: "",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(
                                alpha = if (selectedValue == null) 0f else 1f
                            )
                        )
                    }

                    BarChart(
                        data = barChartData,
                        config = BarChartConfig(
                            thickness = 36.dp,
                        ),
                        yAxisLabel = {

                            val windSpeed = it.toString().padStart(5)

                            Text(
                                text = "$windSpeed km/h",
                                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                textAlign = TextAlign.Start,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        xAxisLabel = {
                            val xLabel = (it as String).take(3)
                            Text(
                                text = xLabel,
                                fontFamily = FontFamily.Monospace,
                                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                modifier = Modifier.padding(vertical = 4.dp),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        overlayDataEntryLabel = { month, value ->
                            val windSpeed = value as Float
                            selectedValue = "${
                                SharedRes.string.average_wind_speed.format(
                                    month = month.lowercase().capitalize(locale = Locale.current)
                                )
                            }: ${windSpeed.round(2)} km/h"
                        },
                        modifier = Modifier.fillMaxSize().weight(1f),
                        animation = ChartAnimation.Sequenced(),
                    )
                }
            }

            "Day Light" -> {
                if (sixMonthsWeatherOverview[2].monthAverages.isNotEmpty()) {
                    val barChartData =
                        BarChartData(categories = sixMonthsWeatherOverview[2].monthAverages.flatMap { monthlyAverage ->
                            listOf(
                                BarChartCategory(
                                    name = monthlyAverage.month.take(3), entries = listOf(
                                        BarChartEntry(
                                            x = monthlyAverage.month,
                                            y = monthlyAverage.averageValue.toFloat(),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                )
                            )
                        })


                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = selectedValue ?: "",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(
                                alpha = if (selectedValue == null) 0f else 1f
                            )
                        )
                    }

                    BarChart(
                        data = barChartData,
                        config = BarChartConfig(
                            thickness = 36.dp,
                        ),
                        yAxisLabel = {

                            val dayLightHours = it.toString().padStart(4)

                            Text(
                                text = "${dayLightHours}h",
                                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                textAlign = TextAlign.Start,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        xAxisLabel = {
                            val xLabel = (it as String).take(3)
                            Text(
                                text = xLabel,
                                fontFamily = FontFamily.Monospace,
                                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                modifier = Modifier.padding(vertical = 4.dp),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        overlayDataEntryLabel = { month, value ->
                            val dayLightHours = value as Float
                            selectedValue = "${
                                SharedRes.string.average_day_light.format(
                                    month = month.lowercase().capitalize(locale = Locale.current)
                                )
                            }: ${dayLightHours.round(2)}h"
                        },
                        modifier = Modifier.fillMaxSize().weight(1f),
                        animation = ChartAnimation.Sequenced(),
                    )
                }
            }

            "Rain" -> {
                if (sixMonthsWeatherOverview[3].monthAverages.isNotEmpty()) {
                    val barChartData =
                        BarChartData(categories = sixMonthsWeatherOverview[3].monthAverages.flatMap { monthlyAverage ->
                            listOf(
                                BarChartCategory(
                                    name = monthlyAverage.month.take(3), entries = listOf(
                                        BarChartEntry(
                                            x = monthlyAverage.month,
                                            y = monthlyAverage.averageValue.toFloat(),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                )
                            )
                        })


                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = selectedValue ?: "",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(
                                alpha = if (selectedValue == null) 0f else 1f
                            )
                        )
                    }

                    BarChart(
                        data = barChartData,
                        config = BarChartConfig(
                            thickness = 36.dp,
                        ),
                        yAxisLabel = {

                            val rainSum = it.toString().padStart(4)

                            Text(
                                text = "${rainSum}mm",
                                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                textAlign = TextAlign.Start,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        xAxisLabel = {
                            val xLabel = (it as String).take(3)
                            Text(
                                text = xLabel,
                                fontFamily = FontFamily.Monospace,
                                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                modifier = Modifier.padding(vertical = 4.dp),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        overlayDataEntryLabel = { month, value ->
                            val rainSum = value as Float
                            selectedValue = "${
                                SharedRes.string.average_rain_sum.format(
                                    month = month.lowercase().capitalize(locale = Locale.current)
                                )
                            }: ${rainSum.round(2)}mm"
                        },
                        modifier = Modifier.fillMaxSize().weight(1f),
                        animation = ChartAnimation.Sequenced(),
                    )
                }
            }
        }

        ChartSelection(
            chartSelectionItems = chartSelectionItems, onChartSelectionItemClicked = {
                onChartSelectionItemClicked(it)
                selectedValue = null
            }, modifier = Modifier.fillMaxWidth()
        )
    }
}