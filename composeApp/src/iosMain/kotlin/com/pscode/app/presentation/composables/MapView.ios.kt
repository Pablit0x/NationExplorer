package com.pscode.app.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import com.pscode.app.domain.model.LocationData
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.MapKit.MKMapView


@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun MapView(locationData: LocationData, modifier: Modifier) {
    val location = CLLocationCoordinate2DMake(
        latitude = locationData.latitude, longitude = locationData.longitude,
    )

    val mkMapView by remember {
        mutableStateOf(MKMapView().apply {
            centerCoordinate = location
            showsUserLocation = false
        })
    }

    UIKitView(modifier = modifier, factory = { mkMapView })
}