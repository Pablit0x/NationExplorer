package com.pscode.app.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LightbulbCircle
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pscode.app.domain.model.TidbitOverview
import com.pscode.app.utils.Constants

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TidbitCard(
    currentTidbitId: Int,
    tidbits: List<TidbitOverview>,
    setCurrentTidbitId: (Int) -> Unit,
    modifier: Modifier
) {

    var isExpended by remember { mutableStateOf(false) }
    val horizontalPagerState = rememberPagerState(pageCount = { Constants.NUMBER_OF_TIDBITS })


    LaunchedEffect(horizontalPagerState.currentPage) {
        setCurrentTidbitId(horizontalPagerState.currentPage)
    }

    ElevatedCard(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LightbulbCircle,
                        contentDescription = "Interesting fact"
                    )
                    Text(text = "Did you know?", style = MaterialTheme.typography.labelLarge)
                }
                IconButton(onClick = { isExpended = !isExpended }) {
                    if (isExpended) {
                        Icon(imageVector = Icons.Default.KeyboardArrowUp, "Close tidbit")
                    } else {
                        Icon(imageVector = Icons.Default.KeyboardArrowDown, "Show tidbit")
                    }
                }
            }

            AnimatedVisibility(isExpended) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    HorizontalPager(
                        state = horizontalPagerState, verticalAlignment = Alignment.CenterVertically
                    ) { currentPageIndex ->
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = tidbits[currentTidbitId].title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = tidbits[currentTidbitId].description,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        PageIndicator(
                            numberOfPages = Constants.NUMBER_OF_TIDBITS,
                            currentPage = horizontalPagerState.currentPage
                        )
                    }
                }
            }
        }
    }
}