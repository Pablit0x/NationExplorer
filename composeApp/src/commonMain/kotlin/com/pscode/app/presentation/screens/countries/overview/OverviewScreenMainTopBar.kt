package com.pscode.app.presentation.screens.countries.overview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes
import com.pscode.app.presentation.composables.AutoResizedText
import com.pscode.app.presentation.screens.countries.overview.states.SearchWidgetState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreenMainTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    searchWidgetState: SearchWidgetState,
    isFiltering: Boolean,
    onFilterClicked: () -> Unit,
    searchTextState: TextFieldValue,
    onSearchTextChange: (String) -> Unit,
    onCloseSearchClicked: () -> Unit,
    onSearchTriggered: () -> Unit,
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            OverviewScreenDefaultTopBar(
                onSearchClicked = onSearchTriggered,
                onFilterClicked = onFilterClicked,
                isFiltering = isFiltering,
                scrollBehavior = scrollBehavior,
            )
        }

        SearchWidgetState.OPENED -> {
            OverviewScreenSearchTopBar(
                textFieldValue = searchTextState,
                onTextChange = onSearchTextChange,
                onCloseClicked = onCloseSearchClicked
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreenDefaultTopBar(
    onSearchClicked: () -> Unit,
    onFilterClicked: () -> Unit,
    isFiltering: Boolean = false,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    CenterAlignedTopAppBar(title = {
        AutoResizedText(
            text = SharedRes.string.countries,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }, actions = {
        IconButton(onClick = onFilterClicked) {
            BadgedBox(badge = {
                if (isFiltering) {
                    Badge(modifier = Modifier.size(3.dp))
                }
            }) {
                Icon(imageVector = Icons.Default.Tune, contentDescription = "Filter")
            }
        }

        IconButton(onClick = onSearchClicked) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        }
    }, scrollBehavior = scrollBehavior)
}

@Composable
fun OverviewScreenSearchTopBar(
    textFieldValue: TextFieldValue, onTextChange: (String) -> Unit, onCloseClicked: () -> Unit
) {

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if (textFieldValue.text.isBlank()) {
            focusRequester.requestFocus()
        }
    }

    Surface(
        modifier = Modifier.statusBarsPadding().fillMaxWidth().height(64.dp),
    ) {

        OutlinedTextField(
            value = textFieldValue.text,
            onValueChange = { onTextChange(it) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (textFieldValue.text.isNotEmpty()) {
                        onTextChange("")
                    } else {
                        onCloseClicked()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Close, contentDescription = "Close Icon"
                    )
                }
            },
            placeholder = {
                Text(text = SharedRes.string.search)
            },
            shape = RoundedCornerShape(percent = 30),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
                .focusRequester(focusRequester = focusRequester)
        )
    }
}
