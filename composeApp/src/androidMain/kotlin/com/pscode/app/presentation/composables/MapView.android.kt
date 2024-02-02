package com.pscode.app.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.pscode.app.domain.model.LocationData

@Composable
actual fun MapView(locationData: LocationData, modifier: Modifier) {

    var isLoading by remember { mutableStateOf(true) }
    val zoomLevel by remember { mutableFloatStateOf((5f)) }

    val cameraPosition by remember {
        mutableStateOf(
            CameraPosition.fromLatLngZoom(
                LatLng(locationData.latitude, locationData.longitude), zoomLevel
            )
        )
    }

    val cameraPositionState = rememberCameraPositionState {
        position = cameraPosition
    }

    GoogleMap(modifier = modifier,
        cameraPositionState = cameraPositionState,
        onMapLoaded = { isLoading = false })

    if (isLoading) {
        FullScreenLoadingIndicator()
    }
}
