package com.pscode.app.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pscode.app.domain.model.LocationData

@Composable
expect fun MapView(locationData: LocationData, countryArea: Double, modifier: Modifier)