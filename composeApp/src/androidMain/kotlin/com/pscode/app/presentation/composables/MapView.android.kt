package com.pscode.app.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.pscode.app.domain.model.GeoLocationOverview

@Composable
actual fun MapView(geoLocationOverview: GeoLocationOverview, modifier : Modifier) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(geoLocationOverview.lat, geoLocationOverview.long),
            5f
        )
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,

        )
}