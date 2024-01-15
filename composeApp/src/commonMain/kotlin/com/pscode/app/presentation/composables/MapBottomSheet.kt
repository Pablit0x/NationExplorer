package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pscode.app.domain.model.LocationOverview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapBottomSheet(
    isMapVisible: Boolean,
    countryArea: Double,
    bottomSheetState: SheetState,
    hideMap: () -> Unit,
    locationOverview: LocationOverview?,
    modifier: Modifier = Modifier
) {
    locationOverview?.let {
        if (isMapVisible) {
            ModalBottomSheet(
                sheetState = bottomSheetState, onDismissRequest = hideMap
            ) {
                MapView(
                    modifier = modifier, countryArea = countryArea, locationOverview = it
                )
            }
        }
    }
}