package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pscode.app.domain.model.SelectableItemWithIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartSelection(
    chartSelectionItems: List<SelectableItemWithIcon>,
    onChartSelectionItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {


    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        chartSelectionItems.forEach { chartItem ->
            FilterChip(
                selected = chartItem.isSelected,
                label = {
                    Icon(imageVector = chartItem.icon, contentDescription = "Chart Icon")
                },
                onClick = { onChartSelectionItemClicked(chartItem.label) },
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}