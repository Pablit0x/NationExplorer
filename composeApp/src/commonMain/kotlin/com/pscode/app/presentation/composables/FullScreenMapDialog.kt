package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pscode.app.domain.model.LocationOverview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenMapDialog(
    countryArea: Double,
    hideMap: () -> Unit,
    locationOverview: LocationOverview?,
    modifier: Modifier = Modifier
) {
    locationOverview?.let {
        Dialog(
            onDismissRequest = hideMap,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Scaffold(modifier = modifier, topBar = {
                CenterAlignedTopAppBar(title = { Text(text = locationOverview.name) }, actions = {
                    IconButton(onClick = hideMap) {
                        Icon(
                            imageVector = Icons.Default.Close, contentDescription = "Close Map"
                        )
                    }
                })
            }) { innerPadding ->

                MapView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    countryArea = countryArea,
                    locationOverview = it
                )
            }
        }
    }
}