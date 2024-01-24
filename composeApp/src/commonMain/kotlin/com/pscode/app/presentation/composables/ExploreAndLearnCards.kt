package com.pscode.app.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes
import com.pscode.app.presentation.screens.countries.detail.states.CardState
import com.pscode.app.presentation.screens.countries.detail.states.CelebrityState
import com.pscode.app.presentation.screens.countries.detail.states.TidbitState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ExploreAndLearnCards(
    tidbitState: TidbitState,
    celebrityState: CelebrityState,
    setCurrentTidbitId: (Int) -> Unit,
    displayShowMapCard: Boolean,
    onShowOnMapCardClicked: () -> Unit,
    onUpdateTidbitCardState: (CardState) -> Unit,
    onUpdateCelebrityCardState: (CardState) -> Unit,
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val tidbitWidth =
        remember(tidbitState.cardState) { if (tidbitState.cardState == CardState.EXPENDED) 1f else 0.6f }
    val celebrityWidth =
        remember(celebrityState.cardState) { if (celebrityState.cardState == CardState.EXPENDED) 1f else 0.6f }

    val showSectionHeadline = remember(
        displayShowMapCard, tidbitState.tidbitData.data, celebrityState.celebrityData.data
    ) {
        displayShowMapCard || tidbitState.tidbitData.data.isNotEmpty() || celebrityState.celebrityData.data.firstOrNull() != null
    }

    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        AnimatedVisibility(visible = showSectionHeadline, enter = fadeIn(), exit = fadeOut()) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                AutoResizedText(
                    text = SharedRes.string.explore_and_learn,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }



        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            state = listState
        ) {

            item {
                ShowOnMapCard(
                    visible = displayShowMapCard, onClick = {
                        scope.launch {
                            listState.animateScrollToItem(0)
                        }
                        onShowOnMapCardClicked()
                    }, modifier = modifier.fillParentMaxWidth(0.6f)
                )
            }

            item {
                TidbitCard(
                    tidbitState = tidbitState,
                    setCurrentTidbitId = setCurrentTidbitId,
                    modifier = Modifier.animateContentSize().fillParentMaxWidth(tidbitWidth),
                    onClick = {
                        when (tidbitState.cardState) {
                            CardState.EXPENDED -> onUpdateTidbitCardState(CardState.COLLAPSED)
                            CardState.COLLAPSED -> {
                                onUpdateTidbitCardState(CardState.EXPENDED)
                                scope.launch {
                                    delay(100)
                                    listState.animateScrollToItem(1)
                                }
                            }
                        }
                    })
            }

            item {
                CelebrityCard(
                    celebrityState = celebrityState,
                    modifier = Modifier.animateContentSize().fillParentMaxWidth(celebrityWidth),
                    onClick = {
                        when (celebrityState.cardState) {
                            CardState.EXPENDED -> onUpdateCelebrityCardState(CardState.COLLAPSED)
                            CardState.COLLAPSED -> {
                                onUpdateCelebrityCardState(CardState.EXPENDED)
                                scope.launch {
                                    delay(100)
                                    listState.animateScrollToItem(2)
                                }
                            }
                        }
                    })
            }
        }

    }

}