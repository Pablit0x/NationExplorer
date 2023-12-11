package com.pscode.app.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.pscode.app.domain.model.GeoLocationOverview
import platform.MapKit.MKMapView
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocationCoordinate2DMake


@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun MapView(geoLocationOverview: GeoLocationOverview, modifier: Modifier) {
    val mkMapView by remember {
        mutableStateOf(MKMapView().apply {
            centerCoordinate = CLLocationCoordinate2DMake(
                latitude = geoLocationOverview.lat, longitude = geoLocationOverview.long
            )
            showsUserLocation = false
        })
    }

    UIKitView(modifier = modifier, factory = { mkMapView })
}