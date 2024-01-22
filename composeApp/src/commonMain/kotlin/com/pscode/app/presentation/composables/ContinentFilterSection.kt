package com.pscode.app.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes
import com.pscode.app.presentation.screens.countries.overview.SelectableItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContinentFilterSection(
    continentsSelectableItems: List<SelectableItem>,
    onContinentFilterItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        Text(
            text = SharedRes.string.continents,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.outline
        )

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(count = 3),
            contentPadding = PaddingValues(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalItemSpacing = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(continentsSelectableItems) { continent ->
                ElevatedFilterChip(selected = continent.isSelected,
                    onClick = { onContinentFilterItemClick(continent.label) },
                    label = {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            AnimatedVisibility(continent.isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Filter Applied",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(
                                text = continent.label, style = MaterialTheme.typography.labelSmall
                            )
                        }
                    })
            }
        }
    }
}