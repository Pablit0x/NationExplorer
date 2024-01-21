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

@Composable
fun AverageTemperatureBarChart(
    sixMonthsWeatherOverview: SixMonthsWeatherOverview, modifier: Modifier = Modifier
) {


    if (sixMonthsWeatherOverview.monthAverages.isNotEmpty()) {

        var selectedValue by remember { mutableStateOf<String?>(null) }

        val barChartData =
            BarChartData(categories = sixMonthsWeatherOverview.monthAverages.flatMap { monthlyAverage ->
                listOf(
                    BarChartCategory(
                        name = monthlyAverage.month.take(3), entries = listOf(
                            BarChartEntry(
                                x = monthlyAverage.month,
                                y = monthlyAverage.averageTemperature.toFloat(),
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    )
                )
            })

        Column(modifier = modifier) {

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
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
                    thickness = 36.dp
                ),
                yAxisLabel = {

                    val temperature = it.toString().padStart(5)

                    Text(
                        text = "$temperature °C",
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        textAlign = TextAlign.Start,
                        fontFamily = FontFamily.Monospace
                    )
                },
                xAxisLabel = {
                    val xLabel = (it as String).take(3)
                    Text(
                        text = xLabel,
                        fontFamily = FontFamily.Monospace,
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        modifier = Modifier.padding(vertical = 4.dp)
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
                modifier = Modifier.fillMaxSize(),
                animation = ChartAnimation.Sequenced(),
            )
        }

    }
}