package com.pscode.app.presentation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.RadioButtonChecked
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes
import com.pscode.app.domain.model.SixMonthsWeatherOverview
import com.pscode.app.domain.model.WeatherOverview
import com.pscode.app.presentation.screens.countries.detail.weatherPlot

data class WeatherItemData(
    val icon: ImageVector, val contentDescription: String, val label: String, val value: String
)

data class WeatherTabItem(
    val title: String, val unselectedIcon: ImageVector, val selectedIcon: ImageVector
)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherCard(
    capitalName: String,
    weatherInCapital: WeatherOverview?,
    sixMonthsWeatherOverview: SixMonthsWeatherOverview,
    modifier: Modifier = Modifier
) {
    var selectedWeatherTab by remember { mutableIntStateOf(0) }
    var listOfWeatherItems by remember { mutableStateOf<List<WeatherItemData>>(emptyList()) }
    val weatherTabItems = listOf(
        WeatherTabItem(
            title = "Live",
            unselectedIcon = Icons.Outlined.RadioButtonChecked,
            selectedIcon = Icons.Filled.RadioButtonChecked
        ), WeatherTabItem(
            title = "Averages",
            unselectedIcon = Icons.Outlined.BarChart,
            selectedIcon = Icons.Filled.BarChart
        )
    )
    val pagerState = rememberPagerState(pageCount = { weatherTabItems.size })

    LaunchedEffect(selectedWeatherTab) {
        pagerState.animateScrollToPage(selectedWeatherTab)
    }

    LaunchedEffect(pagerState.currentPage) {
        selectedWeatherTab = pagerState.currentPage
    }

    LaunchedEffect(weatherInCapital) {
        if (weatherInCapital == null) {
            listOfWeatherItems = emptyList()
        } else {
            listOfWeatherItems = listOf(
                WeatherItemData(
                    icon = Icons.Default.Thermostat,
                    contentDescription = "Temperature",
                    label = SharedRes.string.temperature,
                    value = weatherInCapital.currentTemperature
                ),
                WeatherItemData(
                    icon = Icons.Default.WbCloudy,
                    contentDescription = "Cloudiness",
                    label = SharedRes.string.cloudiness,
                    value = weatherInCapital.cloudCoverPercent
                ),
                WeatherItemData(
                    icon = Icons.Default.WaterDrop,
                    contentDescription = "Humidity",
                    label = SharedRes.string.humidity,
                    value = weatherInCapital.humidity
                ),
                WeatherItemData(
                    icon = Icons.Default.Air,
                    contentDescription = "Wind Speed",
                    label = SharedRes.string.wind_speed,
                    value = weatherInCapital.windSpeed
                ),
                WeatherItemData(
                    icon = Icons.Default.WbSunny,
                    contentDescription = "Sunrise",
                    label = SharedRes.string.sunrise,
                    value = weatherInCapital.sunriseTime
                ),
                WeatherItemData(
                    icon = Icons.Default.WbTwilight,
                    contentDescription = "Sunset",
                    label = SharedRes.string.sunset,
                    value = weatherInCapital.sunsetTime
                ),
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
                text = SharedRes.string.weather_in.format(capital = capitalName),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
            )
        }

        ElevatedCard(modifier = Modifier.fillMaxWidth().height(260.dp)) {
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
                                    contentDescription = "Weather Tab Icon"
                                )
                                Text(text = weatherTabItem.title)
                            }
                        })
                }
            }

            HorizontalPager(
                state = pagerState, modifier = Modifier.fillMaxWidth().weight(1f)
            ) { index ->
                when (index) {
                    0 -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(count = 2),
                            modifier = modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            if (listOfWeatherItems.isEmpty()) items(count = 6) { WeatherItemShimmer() }
                            else {
                                items(items = listOfWeatherItems) { weatherItem ->
                                    WeatherItem(
                                        iconVector = weatherItem.icon,
                                        contentDescription = weatherItem.contentDescription,
                                        label = weatherItem.label,
                                        value = weatherItem.value
                                    )
                                }
                            }
                        }
                    }

                    1 -> {
                        if (sixMonthsWeatherOverview.monthAverages.isNotEmpty()) {
                            weatherPlot(
                                sixMonthsWeatherOverview = sixMonthsWeatherOverview,
                                modifier = Modifier.fillMaxSize().padding(8.dp)
                            )
                        }
                    }
                }

            }


        }


    }
}
