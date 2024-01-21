package com.pscode.app.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.pscode.app.domain.model.SixMonthsWeatherOverview

@Composable
fun AverageTemperatureBarChart(sixMonthsWeatherOverview: SixMonthsWeatherOverview, modifier: Modifier = Modifier) {


    if (sixMonthsWeatherOverview.monthAverages.isNotEmpty()) {

        var selectedValue by remember { mutableStateOf<String?>(null) }

        val barChartData =
            BarChartData(categories = sixMonthsWeatherOverview.monthAverages.flatMap { monthlyAverage ->
                listOf(
                    BarChartCategory(
                        name = monthlyAverage.month.take(3), entries = listOf(
                            BarChartEntry(
                                x = "Average Temperature in ${monthlyAverage.month.lowercase().capitalize(
                                    Locale.current)}",
                                y = monthlyAverage.averageTemperature.toFloat(),
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    )
                )
            })

        Column(modifier = modifier){
            AnimatedVisibility(selectedValue != null, enter = slideInVertically(), exit = fadeOut()){
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
                    Text(text = "$selectedValue", style = MaterialTheme.typography.labelSmall)
                }
            }

            BarChart(
                data = barChartData,
                config = BarChartConfig(
                    thickness = 64.dp
                ),
                yAxisLabel = {
                    val temperature = "$it℃".padStart(6)

                    Text(
                        text = temperature,
                        textAlign = TextAlign.Start,
                        fontFamily = FontFamily.Monospace,
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                    )
                },
                overlayDataEntryLabel = { month, value ->
                    val temperature = value as Float
                    selectedValue = "$month: ${temperature.round(2)}℃"
                },

                modifier = Modifier.fillMaxSize(),
                animation = ChartAnimation.Sequenced(),
            )
        }

    }
}