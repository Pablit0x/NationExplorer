package com.pscode.app.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.pscode.app.domain.model.LocationOverview

@Composable
actual fun MapView(locationOverview: LocationOverview, modifier: Modifier) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(locationOverview.latitude, locationOverview.longitude),
            5f
        )
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,

        )
}