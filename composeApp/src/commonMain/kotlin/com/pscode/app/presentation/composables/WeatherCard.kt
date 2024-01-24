package com.pscode.app.presentation.composables

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.RadioButtonChecked
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.carlosgub.kotlinm.charts.round
import com.pscode.app.SharedRes
import com.pscode.app.domain.model.CurrentWeatherData
import com.pscode.app.domain.model.SixMonthsWeatherOverview
import com.pscode.app.domain.model.WeatherInfo
import com.pscode.app.presentation.screens.countries.overview.SelectableItemWithIcon
import kotlin.math.roundToInt

data class WeatherItemData(
    val icon: ImageVector,
    val contentDescription: String,
    val label: String,
    val value: String,
    val unit: String
)

data class WeatherTabItem(
    val title: String, val unselectedIcon: ImageVector, val selectedIcon: ImageVector
)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherCard(
    countryName: String,
    weatherInfo: WeatherInfo?,
    weatherInCapital: CurrentWeatherData?,
    sixMonthsWeatherOverview: List<SixMonthsWeatherOverview>,
    chartSelectionItems: List<SelectableItemWithIcon>,
    onChartSelectionItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedWeatherTab by remember { mutableIntStateOf(0) }
    var listOfWeatherItems by remember { mutableStateOf<List<WeatherItemData>?>(null) }

    val transition = rememberInfiniteTransition()

    val animatedAlpha by transition.animateFloat(
        initialValue = 0.2f, targetValue = 1f, animationSpec = infiniteRepeatable(
            tween(durationMillis = 2000), repeatMode = RepeatMode.Reverse
        )
    )

    val liveWeatherTabItem = WeatherTabItem(
        title = "Live",
        unselectedIcon = Icons.Outlined.RadioButtonChecked,
        selectedIcon = Icons.Filled.RadioButtonChecked
    )

    val averagesWeatherTabItem = WeatherTabItem(
        title = "Averages",
        unselectedIcon = Icons.Outlined.BarChart,
        selectedIcon = Icons.Filled.BarChart
    )

    val weatherTabItems = listOf(liveWeatherTabItem, averagesWeatherTabItem)

    val pagerState = rememberPagerState(pageCount = { weatherTabItems.size })

    LaunchedEffect(selectedWeatherTab) {
        pagerState.animateScrollToPage(selectedWeatherTab)
    }

    LaunchedEffect(pagerState.currentPage) {
        selectedWeatherTab = pagerState.currentPage
    }

    LaunchedEffect(weatherInfo?.currentWeatherData) {
        weatherInfo?.currentWeatherData?.let { currentWeatherData ->
            listOfWeatherItems = listOf(
                WeatherItemData(
                    icon = Icons.Default.Thermostat,
                    contentDescription = "Feels Like",
                    label = SharedRes.string.feels_like,
                    value = currentWeatherData.feelsLike.round(1),
                    unit = "°C"
                ), WeatherItemData(
                    icon = Icons.Default.WbCloudy,
                    contentDescription = "Cloudiness",
                    label = SharedRes.string.cloudiness,
                    value = currentWeatherData.cloudiness.toString(),
                    unit = "%"
                ), WeatherItemData(
                    icon = Icons.Default.WaterDrop,
                    contentDescription = "Humidity",
                    label = SharedRes.string.humidity,
                    value = currentWeatherData.humidity.toString(),
                    unit = "%"
                ), WeatherItemData(
                    icon = Icons.Default.Air,
                    contentDescription = "Wind Speed",
                    label = SharedRes.string.wind_speed,
                    value = currentWeatherData.windSpeed.round(1),
                    unit = "km/h"
                ), WeatherItemData(
                    icon = Icons.Default.Speed,
                    contentDescription = "Pressure",
                    label = SharedRes.string.pressure,
                    value = currentWeatherData.hPa.round(1),
                    unit = "hPa"
                ), WeatherItemData(
                    icon = Icons.Default.Visibility,
                    contentDescription = "Visibility",
                    label = SharedRes.string.visibility,
                    value = currentWeatherData.visibility.roundToInt().toString(),
                    unit = "m"

                )
            )
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = SharedRes.string.weather_in.format(capital = countryName),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
            )
        }

        ElevatedCard(modifier = Modifier.fillMaxWidth().height(390.dp)) {
            TabRow(
                selectedTabIndex = selectedWeatherTab,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                weatherTabItems.forEachIndexed { index, weatherTabItem ->
                    Tab(selected = index == selectedWeatherTab,
                        onClick = { selectedWeatherTab = index },
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = if (index == selectedWeatherTab) weatherTabItem.selectedIcon else weatherTabItem.unselectedIcon,
                                    contentDescription = "Weather Tab Icon",
                                    tint = if (index == 0) Color.Red.copy(alpha = animatedAlpha) else LocalContentColor.current
                                )
                                Text(text = weatherTabItem.title)
                            }
                        })
                }
            }

            HorizontalPager(
                state = pagerState, modifier = Modifier.fillMaxWidth()
            ) { index ->
                when (index) {
                    weatherTabItems.indexOf(liveWeatherTabItem) -> {
                        DetailedLiveWeatherCard(
                            weatherData = weatherInfo?.currentWeatherData,
                            listOfWeatherItems = listOfWeatherItems,
                            modifier = Modifier.fillMaxSize().padding(8.dp)
                        )
                    }

                    weatherTabItems.indexOf(averagesWeatherTabItem) -> {
                        AveragesBarChart(
                            sixMonthsWeatherOverview = sixMonthsWeatherOverview,
                            chartSelectionItems = chartSelectionItems,
                            onChartSelectionItemClicked = onChartSelectionItemClicked,
                            modifier = Modifier.fillMaxSize().padding(8.dp)
                        )
                    }

                }

            }


        }


    }
}
