package com.pscode.app.presentation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes
import com.pscode.app.presentation.screens.countries.detail.states.CardState
import com.pscode.app.presentation.screens.countries.detail.states.TidbitState
import com.pscode.app.utils.Constants
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TidbitCard(
    tidbitState: TidbitState,
    setCurrentTidbitId: (Int) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier
) {
    if (tidbitState.tidbitData.data.isNotEmpty()) {
        val horizontalPagerState = rememberPagerState(pageCount = { Constants.NUMBER_OF_TIDBITS })
        val scope = rememberCoroutineScope()

        LaunchedEffect(horizontalPagerState.currentPage) {
            setCurrentTidbitId(horizontalPagerState.currentPage)
        }

        ElevatedCard(modifier = modifier, shape = RoundedCornerShape(10)) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .noRippleClickable { onClick() },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = "Interesting fact"
                        )

                        Text(
                            text = SharedRes.string.did_you_know,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    if (tidbitState.cardState == CardState.EXPENDED) {
                        Icon(imageVector = Icons.Default.KeyboardArrowUp, "Close tidbit")
                    } else {
                        Icon(imageVector = Icons.Default.KeyboardArrowDown, "Show tidbit")
                    }

                }

                if (tidbitState.cardState == CardState.EXPENDED) {
                    Column(
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HorizontalPager(
                            modifier = Modifier.weight(1f),
                            state = horizontalPagerState,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize().padding(top = 24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = tidbitState.tidbitData.data[tidbitState.currentId].title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = tidbitState.tidbitData.data[tidbitState.currentId].description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        Box(
                            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                        ) {
                            PageIndicator(numberOfPages = Constants.NUMBER_OF_TIDBITS,
                                currentPage = horizontalPagerState.currentPage,
                                onClick = {
                                    scope.launch {
                                        horizontalPagerState.animateScrollToPage(it)
                                    }
                                })
                        }
                    }
                }
            }
        }
    }
}