package com.pscode.app.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pscode.app.domain.model.LocationOverview

@Composable
expect fun MapView(locationOverview: LocationOverview, countryArea: Double, modifier: Modifier)