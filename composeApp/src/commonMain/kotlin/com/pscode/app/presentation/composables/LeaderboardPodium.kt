package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pscode.app.domain.model.Result
import com.pscode.app.presentation.screens.countries.flag_game.leaderboard.TopRankItem
import com.pscode.app.presentation.theme.Gradients

@Composable
fun LeaderboardPodium(
    topThreeResultData: List<Result>, modifier: Modifier = Modifier
) {

    if (topThreeResultData.size == 3) {
        val firstPlace = topThreeResultData[0]
        val secondPlace = topThreeResultData[1]
        val thirdPlace = topThreeResultData[2]


        Row(
            modifier = modifier,
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TopRankItem(
                rank = 2,
                resultData = secondPlace,
                borderGradientColors = Gradients.SILVER,
                modifier = Modifier.weight(1f).height(165.dp)
            )

            TopRankItem(
                rank = 1,
                resultData = firstPlace,
                borderGradientColors = Gradients.GOLD,
                modifier = Modifier.weight(1f).height(175.dp)
            )

            TopRankItem(
                rank = 3,
                resultData = thirdPlace,
                borderGradientColors = Gradients.BRONZE,
                modifier = Modifier.weight(1f).height(155.dp)
            )
        }
    }

}