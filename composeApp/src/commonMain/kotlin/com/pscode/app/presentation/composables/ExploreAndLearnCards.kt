package com.pscode.app.presentation.composables

import androidx.compose.animation.animateContentSize
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
import com.pscode.app.domain.model.CelebrityOverview
import com.pscode.app.domain.model.TidbitOverview
import com.pscode.app.presentation.screens.countries.detail.CardState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ExploreAndLearnCards(
    currentTidbitId: Int,
    tidbits: List<TidbitOverview>,
    setCurrentTidbitId: (Int) -> Unit,
    displayShowMapCard: Boolean,
    onShowOnMapCardClicked: () -> Unit,
    celebrity: CelebrityOverview?,
    celebrityCardState: CardState,
    tidbitCardState: CardState,
    onUpdateTidbitCardState: (CardState) -> Unit,
    onUpdateCelebrityCardState: (CardState) -> Unit,
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val tidbitWidth =
        remember(tidbitCardState) { if (tidbitCardState == CardState.EXPENDED) 1f else 0.6f }
    val celebrityWidth =
        remember(celebrityCardState) { if (celebrityCardState == CardState.EXPENDED) 1f else 0.6f }

    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
            AutoResizedText(
                text = SharedRes.string.explore_and_learn,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
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
                TidbitCard(currentTidbitId = currentTidbitId,
                    tidbits = tidbits,
                    setCurrentTidbitId = setCurrentTidbitId,
                    modifier = Modifier.animateContentSize().fillParentMaxWidth(tidbitWidth),
                    cardState = tidbitCardState,
                    onClick = {
                        when (tidbitCardState) {
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
                CelebrityCard(celebrity = celebrity,
                    modifier = Modifier.animateContentSize().fillParentMaxWidth(celebrityWidth),
                    cardState = celebrityCardState,
                    onClick = {
                        when (celebrityCardState) {
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