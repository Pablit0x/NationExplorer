package com.pscode.app.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class SelectableItem(
    val label: String,
    val isSelected: Boolean
)

data class SelectableItemWithIcon(
    val label: String,
    val isSelected: Boolean,
    val icon: ImageVector
)